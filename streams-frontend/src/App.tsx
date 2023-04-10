import React from 'react';
import './App.css';
import {Routes, Route, BrowserRouter} from "react-router-dom";
import {QueryClient, QueryClientProvider} from "@tanstack/react-query";
import Login from "./components/Login";
import Register from "./components/Register";
import axios from "axios";
import ShowMessages from "./components/ShowMessages";
import {createTheme, ThemeProvider} from "@mui/material";
import HeaderBar from "./components/HeaderBar";

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
    axios.defaults.baseURL = "http://localhost:8081";

    return (
        <QueryClientProvider client={queryClient}>
            <ThemeProvider theme={theme}>
                <BrowserRouter>
                    <HeaderBar/>
                    <Routes>
                        <Route path="/" element={<Login/>}/>
                        <Route path="/register" element={<Register/>}/>
                        <Route path="/showmessages" element={<ShowMessages/>}/>
                    </Routes>
                </BrowserRouter>
            </ThemeProvider>
        </QueryClientProvider>
    );
}

export default App;
