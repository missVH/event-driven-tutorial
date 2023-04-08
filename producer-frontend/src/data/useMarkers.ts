import axios from "axios";
import {useQuery} from "@tanstack/react-query";
import {MarkerType} from "../types/MarkerType";

export function useMarkers() {
    const {
        isLoading: isLoadingMarker,
        isError: isErrorMarker,
        data: markersData,
    } = useQuery(["markers"],
        () => getMarkers());

    return {
        markersData,
        isLoading: isLoadingMarker,
        isError: isErrorMarker,
    }
}

async function getMarkers() {
    const markers = await axios.get<MarkerType[]>(`api/marker/all`);
    return markers.data;
}