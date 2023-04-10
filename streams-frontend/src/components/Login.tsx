import "./Login.css";
import {Button, Container, Box, TextField, Alert, Card} from "@mui/material";
import {postLogin} from "../data/userFunctions";
import {useState} from "react";
import {useNavigate} from "react-router-dom";
import {UserModel} from "../types/UserModel";
import logo from "../assets/logoInvisible.png";

export default function Login() {
    const [userName, setUserName] = useState("");
    const [password, setPassword] = useState("");
    const [userNameError, setUserNameError] = useState(false);
    const [passwordError, setPasswordError] = useState(false);
    const navigate = useNavigate();

    const handleSubmit = async (e: { preventDefault: () => void; }) => {
        e.preventDefault();
        if (userName.length !== 0 && password.length !== 0) {
            const usermodel = UserModel(userName, password);
            await postLogin(usermodel).then(async response => {
                if (response.status === 200) {
                    localStorage.setItem("jwtToken", response.data.jwtToken);
                    localStorage.setItem("loggedInUser", JSON.stringify(response.data.username));
                    navigate("/showmessages");
                }
            }).catch(e => {
                if (e.response != null) {
                    if (e.response.status === 403) {
                        setUserNameError(false);
                        setPasswordError(false);
                        if (e.response.data === "username") {
                            setUserNameError(true);
                        }
                        if (e.response.data === "password") {
                            setPasswordError(true);
                        }
                    }
                }
            });
        } else {
            if (userName.length === 0) {
                setUserNameError(true);
            }
            if (password.length === 0) {
                setPasswordError(true);
            }
        }
    }

    return (
        <Container className="loginContainer">
            <Card className="align">
                <img className="loginLogo" src={logo} alt={"logo"}/>
                <Box component="form" sx={{
                    '& .MuiTextField-root': {m: 1, width: '25ch'},
                }} onSubmit={handleSubmit}>
                    <div>
                        <TextField label="Username" variant="outlined" onChange={e => setUserName(e.target.value)}/>
                    </div>
                    <div>
                        {userNameError && (
                            <Alert severity="error">username does not exist</Alert>
                        )}
                        <TextField label="Password" variant="outlined" type="password"
                                   onChange={e => setPassword(e.target.value)}/></div>
                    {passwordError && (
                        <Alert severity="error">wrong password</Alert>
                    )}
                    <Button type="submit" variant={"contained"} color={"primary"} className="signinButton">Sign in</Button>
                </Box>
                <p id="newUser">
                    New user? <a href="/register">Register</a>
                </p>
            </Card>
        </Container>)
}