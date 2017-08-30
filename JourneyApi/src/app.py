from collections import OrderedDict

from flask import Flask, Response, request
import requests
import json
import properties

app = Flask(__name__)

app_id = properties.app_id
app_key = properties.app_key


@app.route("/journey", methods=["GET"])
def home():
    body = ""
    status = 500
    bus_only = request.args.get("bus_only")
    mode = "tube,overground"
    if bus_only and bus_only.lower() == "true":
        mode = "bus"
    body, status = get_journey_info(request.args.get('from'), request.args.get('to'), mode)
    return Response(body, mimetype="application/json", status=status)


def get_journey_info(station_from: str, station_to: str, mode="tube,overground"):
    station_from = station_from.replace(" ", "%20")
    station_to = station_to.replace(" ", "%20")
    param_dict = {"app_id": app_id, "app_key": app_key, "mode": mode}
    res_json = request_journey(station_from, station_to, param_dict)

    if "Tfl.Api.Presentation.Entities.JourneyPlanner.DisambiguationResult" in res_json["$type"]:
        disambs = solve_disambiguations(res_json, station_from, station_to, mode.split(","))
        res_json = request_journey(disambs[0], disambs[1], param_dict)

    if "exceptionType" in res_json and res_json["exceptionType"] == "EntityNotFoundException":
        return None, 502

    arrival_time, journey_name, is_disrupted = extract_departure_details(res_json)
    changes = extract_changes(res_json)

    res_dict = {"arrivalTime": arrival_time, "name": journey_name, "isDisrupted": is_disrupted, "changes": changes}
    return json.dumps(res_dict), 200


def extract_changes(journey_json: dict):
    journey = journey_json["journeys"][0]
    legs = journey["legs"]
    changes = []
    for leg in legs:
        info = leg["instruction"]["summary"]
        if " to " in info:
            split = info.split(" to ")
            changes.append({"line": split[0], "stop": split[1]})

    return changes


def extract_departure_details(journey_json: dict):
    journey = journey_json["journeys"][0]
    arrival_time = journey["arrivalDateTime"].split("T")[1]
    journey_name = journey["legs"][0]["instruction"]["detailed"]
    is_disrupted = journey["legs"][0]["isDisrupted"]

    return arrival_time, journey_name, is_disrupted


def request_journey(station_from: str, station_to: str, param_dict: dict):
    url = "https://api.tfl.gov.uk/Journey/JourneyResults/{}/to/{}".format(station_from, station_to)
    res = make_get_request(url, param_dict)

    return OrderedDict(json.loads(res))


def solve_disambiguations(ordered_json: dict, station_from: str, station_to: str, modes: list) -> (str, str):
    station_from_ics = station_from
    station_to_ics = station_to

    if not ordered_json["fromLocationDisambiguation"]["matchStatus"] == "identified":
        for ele in ordered_json["fromLocationDisambiguation"]["disambiguationOptions"]:
            if "icsCode" not in ele["place"]:
                continue
            vals = ele["place"]["modes"]
            if is_right_mode(modes, vals):
                station_from_ics = ele["place"]["icsCode"]
                break

    if not ordered_json["toLocationDisambiguation"]["matchStatus"] == "identified":
        for ele in ordered_json["toLocationDisambiguation"]["disambiguationOptions"]:
            if "icsCode" not in ele["place"]:
                continue
            vals = ele["place"]["modes"]
            if is_right_mode(modes, vals):
                station_to_ics = ele["place"]["icsCode"]
                break

    print(station_from_ics, station_to_ics)

    return station_from_ics, station_to_ics


def is_right_mode(mode_list, vals):
    for mode in mode_list:
        if mode in vals:
            return True
    return False


def make_get_request(url, params) -> str:
    return requests.get(url, params=params).content.decode()
