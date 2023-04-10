import {AppBar, Button, Toolbar, Typography} from "@mui/material";
import {useNavigate} from "react-router-dom";
import "./HeaderBar.css"
import logo from "../assets/logofull.png";

export default function HeaderBar() {
    let navigate = useNavigate();
    const user = localStorage.getItem("loggedInUser") ? "loggedIn" : null;

    const logout = () => {
        localStorage.removeItem("loggedInUser");
        localStorage.removeItem("jwtToken");
        navigate("/");
    }

    const login = () => {
        navigate("/")
    }

    return (<>
        <AppBar color="secondary" position="relative"><Toolbar id="toolBar">
            <Button onClick={() => navigate("/")}>
                <img className="headerLogo" src={logo} alt={"logo"}/>
            </Button>
            <div className="loginButtonContainer">
                {user === null &&
                    <Button onClick={login} variant={"outlined"} color={"primary"}>
                        <Typography color={"primary"}>
                            Login
                        </Typography>
                    </Button>}
                {user !== null &&
                    <Button onClick={logout} variant={"outlined"} color={"primary"}>
                        <Typography color={"primary"}>
                            Logout
                        </Typography>
                    </Button>}
            </div>
        </Toolbar>
        </AppBar>
    </>)
}