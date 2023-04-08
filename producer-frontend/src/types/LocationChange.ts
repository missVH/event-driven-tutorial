export interface LocationChange {
    username: string;
    markerId: number;
    timeChange: string;
    arrival: boolean;
}

export const LocationChange = (username: string, markerId: number, timeChange: string, arrival: boolean) => {
    return {username, markerId, timeChange, arrival}
}