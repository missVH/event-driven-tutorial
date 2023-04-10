import axios from "axios";
import {useEffect} from "react";
import {useNavigate} from "react-router-dom";
import BarChartTimes from './charts/BarChartTimes';
import BarTopVisited from "./charts/BarTopVisited";
import DougnutTimeAt from "./charts/DougnutTimeAt";
import {Card, Container} from "@mui/material";
import "./ShowMessages.css";


export default function ShowMessages() {
    const loggedInUser: string | null = localStorage.getItem("loggedInUser") ? JSON.parse(localStorage.getItem("loggedInUser")!) : null;
    axios.defaults.headers.common['Authorization'] = localStorage.getItem("jwtToken") ? "Bearer " + localStorage.getItem("jwtToken") : null;
    let navigate = useNavigate();
    useEffect(() => {
        if (loggedInUser === null) {
            navigate("/");
        }
    }, [loggedInUser, navigate])

    return <Container id="graph-grid">
        <Card className="grid-cards" id="averageTime">
            <DougnutTimeAt username={loggedInUser!}/>
        </Card>
        <Card className="grid-cards" id="mostVisited">
            <BarTopVisited/>
        </Card>
        <Card className="grid-cards" id="popularTimes">
            <BarChartTimes/>
        </Card>
    </Container>
}