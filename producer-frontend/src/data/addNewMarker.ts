import {MarkerType} from "../types/MarkerType";
import axios from "axios";

export async function addNewMarker(marker: MarkerType) {
    const newMarker = await axios.post<string>(`api/marker/new`, marker);
    return newMarker.data;
}