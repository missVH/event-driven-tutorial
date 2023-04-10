export interface UserModel {
    username: String
    password: String
}

export const UserModel = (username: String, password: String) => { return { username,password } }