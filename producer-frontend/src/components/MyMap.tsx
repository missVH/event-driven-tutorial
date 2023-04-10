import ReactMapGL, {Marker, Popup} from "react-map-gl";
import React, {useEffect, useState} from "react";
import {MarkerType} from "../types/MarkerType";
import {addNewMarker} from "../data/addNewMarker";
import "./MyMap.css"
import {Button, Box, FormControlLabel, FormGroup, Switch, Typography} from "@mui/material";
import {addNewLocationChange} from "../data/locationFunctions";
import {LocationChange} from "../types/LocationChange";
import {LoggedInUser} from "../types/LoggedInUser";
import markerImage from "../assets/locationlogo.png"
import {useMarkers} from "../data/useMarkers";
import axios from "axios";
import {useNavigate} from "react-router-dom";

export default function MyMap() {
    const loggedInUser: LoggedInUser | null = localStorage.getItem("loggedInUser") ? JSON.parse(localStorage.getItem("loggedInUser")!) : null;
    axios.defaults.headers.common['Authorization'] = localStorage.getItem("jwtToken") ? "Bearer " + localStorage.getItem("jwtToken") : null;
    let navigate = useNavigate();
    useEffect(() => {
        if (loggedInUser === null) {
            navigate("/");
        }
    }, [loggedInUser, navigate])

    const [lngLat, setlngLat] = useState([0, 0]);
    const [markers, setMarkers] = useState<MarkerType[]>([]);
    const [selectedMarker, setSelectedMarker] = useState<MarkerType | null>();
    const [reload, setReload] = useState(false);
    const [markerView, setMarkerView] = useState(true);
    const [present, setPresent] = useState(false);
    const [startedMarkers, setStartedMarkers] = useState(false);
    const {markersData, isLoading, isError} = useMarkers();

    useEffect(() => {
        if (markers !== undefined) {
            const marker = MarkerType(markers.length, (Math.round(lngLat.at(0)! * 1000) / 1000).toString(), (Math.round(lngLat.at(1)! * 1000) / 1000).toString())
            console.log(marker);
            addNewMarker(marker);
            setMarkers(markers.concat(marker));
            console.log(markers);
        } else {
            if (lngLat.at(0)! !== 0 && lngLat.at(1)! !== 0) {
                const marker = MarkerType(0, (Math.round(lngLat.at(0)! * 1000) / 1000).toString(), (Math.round(lngLat.at(0)! * 1000) / 1000).toString());
                addNewMarker(marker);
                setMarkers([marker]);
            }
        }
    }, [reload]);

    function handleMapClick(e: any) {
        if (markerView) {
            setlngLat([e.lngLat.lng, e.lngLat.lat]);
            setReload(!reload)
        }
    }

    function leftLocation(markerId: number) {
        const user: string = JSON.parse(localStorage.getItem("loggedInUser")!)
        const locationChange = LocationChange(user, markerId, new Date().toISOString(), false);
        addNewLocationChange(locationChange);
        setPresent(false);
    }

    function arrivedAtLocation(markerId: number) {
        const user: string = JSON.parse(localStorage.getItem("loggedInUser")!)
        const locationChange = LocationChange(user, markerId, new Date().toISOString(), true);
        addNewLocationChange(locationChange);
        setPresent(true);
    }
    if (isLoading) {
        return <div>Loading...</div>
    } else if (isError) {
        return <div>Could not fetch Markers</div>
    } else {
        if (!startedMarkers) {
            if (markersData !== undefined && markersData.length !== 0) {
                setMarkers(markers.concat(markersData));
            }

            setStartedMarkers(true);
        }
        console.log("hello");
        return <div className="mymap-container">
            {!markerView &&
                <Typography>Register at a location by clicking on a marker or switch to add marker view</Typography>
            }
            {markerView &&
                <Typography>Create a new marker by clicking the map or switch to location view</Typography>
            }
            <FormGroup>
                <FormControlLabel control={<Switch defaultChecked/>} onChange={() => {
                    setMarkerView(!markerView)
                }} label="add marker view"/>
            </FormGroup>
            <div className="map-container">
                <Box sx={{width: '100%', height: '100%'}}>
                    <ReactMapGL
                        mapboxAccessToken={""}
                        initialViewState={{
                            longitude: 4.9,
                            latitude: 50.6,
                            zoom: 6.5
                        }}
                        mapStyle="mapbox://styles/mapbox/streets-v9"
                        onClick={handleMapClick}
                        id="myMap"
                    >
                        {markers !== undefined && markers.map((marker: MarkerType) => {
                            return <Marker
                                key={marker.markerId}
                                longitude={parseFloat(marker.longitude)}
                                latitude={parseFloat(marker.latitude)}
                                onClick={() => {
                                    setSelectedMarker(marker)
                                }}
                            >
                                <img src={markerImage} alt="marker" id="markerImage"/>
                            </Marker>
                        })}
                        {selectedMarker &&
                            <Popup
                                latitude={parseFloat(selectedMarker.latitude)}
                                longitude={parseFloat(selectedMarker.longitude)}
                                closeButton={true}
                                closeOnClick={false}
                                onClose={() => setSelectedMarker(null)}
                            >
                                <p>{Math.round(Number(selectedMarker.latitude) * 100) / 100} : {Math.round(Number(selectedMarker.longitude) * 100) / 100} </p>
                                {!present &&
                                    <Button size="small" onClick={() => arrivedAtLocation(selectedMarker?.markerId)}>Hier
                                        aanwezig</Button>}
                                {present && <Button size="small" onClick={() => leftLocation(selectedMarker?.markerId)}>Hier
                                    vertrokken</Button>}
                            </Popup>
                        }
                    </ReactMapGL>
                </Box>
            </div>
        </div>
    }
}