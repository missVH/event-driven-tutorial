import {Alert, Box, Button, Card, Container, TextField} from "@mui/material";
import {useState} from "react";
import {useNavigate} from "react-router-dom";
import {UserModel} from "../types/UserModel";
import {postRegister} from "../data/userFunctions";
import logo from "../assets/logoInvisible.png";

export default function Register() {
    const [userName, setUserName] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState(false);
    const [validPassword, setValidPassword] = useState(false);
    const [validUsername, setValidUsername] = useState(false);
    const navigate = useNavigate();

    const handleSubmit = async (e: { preventDefault: () => void; }) => {
        e.preventDefault();
        const usermodel = UserModel(userName, password);
        await postRegister(usermodel).then(async response => {
            if (response.status === 200) {
                navigate("/");
            }
        }).catch(e => {
            if (e.response.status === 403) {
                setError(true);
                setValidUsername(false);
            }
        })
    }
    return (
        <>
            <Container className="loginContainer">
                <Card className="align">
                    <img className="loginLogo" src={logo} alt={"logo"}/>
                    <Box component="form" sx={{
                        '& .MuiTextField-root': {m: 1, width: '25ch'},
                    }} onSubmit={handleSubmit}>
                        <div>
                            <TextField label="Username" variant="outlined" onChange={e => {
                                setUserName(e.target.value)
                                setValidUsername(true);
                            }}/>
                        </div>
                        <div>
                            {error && (
                                <Alert severity="error">username taken</Alert>
                            )}
                            <TextField label="Password" variant="outlined" type="password"
                                       onChange={e => {
                                           setPassword(e.target.value)
                                           setValidPassword(true)
                                       }}/></div>
                        <Button type="submit" variant={"contained"} disabled={!validUsername || !validPassword} className="signinButton">Register</Button>
                    </Box>
                </Card>
            </Container>
        </>
    )
}