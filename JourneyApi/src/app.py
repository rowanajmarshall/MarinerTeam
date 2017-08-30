from collections import OrderedDict

from flask import Flask, Response, request
import requests
import json

app = Flask(__name__)

app_id = "4edf17eb"
app_key = "b636e2f5137c96854e78e5e2a4bb1326"


@app.route("/journey", methods=["GET"])
def home():
    body = get_journey_info(request.args.get('from'), request.args.get('to'))
    return Response(body, mimetype="application/json")


def get_journey_info(station_from: str, station_to: str, mode="tube,overground"):
    station_from = station_from.replace(" ", "%20")
    station_to = station_to.replace(" ", "%20")
    param_dict = {"app_id": app_id, "app_key": app_key, "mode": mode}
    res_json = request_journey(station_from, station_to, param_dict)

    if "Tfl.Api.Presentation.Entities.JourneyPlanner.DisambiguationResult" in res_json["$type"]:
        disambs = solve_disambiguations(res_json)
        res_json = request_journey(disambs[0], disambs[1], param_dict)

    return extract_departure_details(res_json)


def extract_departure_details(journey_json: dict):
    journey = journey_json["journeys"][0]
    arrival_time = journey["arrivalDateTime"].split("T")[1]
    journey_name = journey["legs"][0]["instruction"]["detailed"]
    is_disrupted = journey["legs"][0]["isDisrupted"]

    res_dict = {"arrivalTime": arrival_time, "name": journey_name, "isDisrupted": is_disrupted}

    return json.dumps(res_dict)

def request_journey(station_from: str, station_to: str, param_dict: dict):
    url = "https://api.tfl.gov.uk/Journey/JourneyResults/{}/to/{}".format(station_from, station_to)
    res = make_get_request(url, param_dict)

    return OrderedDict(json.loads(res))

def solve_disambiguations(ordered_json: dict) -> (str, str):
    station_from_ics = ""
    station_to_ics = ""

    for ele in ordered_json["fromLocationDisambiguation"]["disambiguationOptions"]:
        if "icsCode" not in ele["place"]:
            continue
        vals = ele["place"]["modes"]
        if "tube" in vals or "overground" in vals:
            station_from_ics = ele["place"]["icsCode"]
            break

    for ele in ordered_json["toLocationDisambiguation"]["disambiguationOptions"]:
        if "icsCode" not in ele["place"]:
            continue
        vals = ele["place"]["modes"]
        if "tube" in vals or "overground" in vals:
            station_to_ics = ele["place"]["icsCode"]
            break
    return station_from_ics, station_to_ics


def make_get_request(url, params) -> str:
    return requests.get(url, params=params).content.decode()
