import axios from "axios";
import {UserModel} from "../types/UserModel";

export async function postLogin(userModel: UserModel) {
    return await axios.post("/api/account/login", userModel);
}

export async function postRegister(userModel: UserModel) {
    return await axios.post("/api/account/register", userModel);
}