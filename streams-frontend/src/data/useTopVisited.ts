import axios from "axios";
import {useQuery} from "@tanstack/react-query";
import {GraphDataType} from "../types/GraphDataType";

export default function useTopVisited() {
    const {
        isLoading,
        isError,
        data: chartData,
    } = useQuery(["topVisited"],
        () => getTopVisited());

    return {
        chartData,
        isLoading,
        isError
    }
}

async function getTopVisited() {
    const topVisited = await axios.get(`api/message/topVisited`);
    return topVisited.data;
}