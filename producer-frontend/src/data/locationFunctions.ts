import axios from "axios";
import {LocationChange} from "../types/LocationChange";

export async function addNewLocationChange(locationChange: LocationChange) {
    console.log(locationChange);
    const newMarker = await axios.put(`api/location/registerLocationChange`,locationChange);
    return newMarker.data;
}