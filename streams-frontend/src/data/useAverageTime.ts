import axios from "axios";
import {useQuery} from "@tanstack/react-query";
import {GraphDataType} from "../types/GraphDataType";

export default function useAverageTime(username: string) {
    const {
        isLoading,
        isError,
        data: chartData,
    } = useQuery(["averageTime"],
        () => getAverageTimes(username));

    return {
        chartData,
        isLoading,
        isError
    }
}

async function getAverageTimes(username: string) {
    const chartData = await axios.get(`api/message/averageTime/${username}`);
    return chartData.data;
}