import axios from "axios";
import {useQuery} from "@tanstack/react-query";
import {PopularTimes} from "../types/PopularTimes";

export default function usePopularTimes() {
    const {
        isLoading,
        isError,
        data: chartData,
    } = useQuery(["popularTimes"],
        () => getPopularTimes());

    return {
        chartData,
        isLoading,
        isError
    }
}

async function getPopularTimes() {
    const pokerSessions = await axios.get(`api/message/popularTimes`);
    return pokerSessions.data;
}