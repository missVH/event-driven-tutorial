import React from 'react';
import {createTheme, ThemeProvider} from "@mui/material";
import axios from "axios";
import {QueryClient, QueryClientProvider} from "@tanstack/react-query";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import HeaderBar from "./components/HeaderBar";
import Register from "./components/Register";
import Login from "./components/Login";
import MyMap from "./components/MyMap";
import "mapbox-gl/dist/mapbox-gl.css";

const theme = createTheme({
    palette: {
        primary: {
            light: '#3B4D7D',
            main: '#202a44',
            dark: '#14192A',
            contrastText: '#fff',
        },
        secondary: {
            light: '#5CCEFF',
            main: '#0098da',
            dark: '#004766',
            contrastText: '#000',
        },
    },
});

function App() {
    const queryClient = new QueryClient();
    axios.defaults.baseURL = "http://localhost:8080";

    return (
        <QueryClientProvider client={queryClient}>
            <ThemeProvider theme={theme}>
                <BrowserRouter>
                    <HeaderBar/>
                    <Routes>
                        <Route path="/" element={<Login/>}/>
                        <Route path="/map" element={<MyMap/>}/>
                        <Route path="/register" element={<Register/>}/>
                    </Routes>
                </BrowserRouter>
            </ThemeProvider>
        </QueryClientProvider>
    );
}

export default App;
