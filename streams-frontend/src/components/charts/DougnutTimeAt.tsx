import {Doughnut} from "react-chartjs-2";
import useAverageTime from "../../data/useAverageTime";
import {CircularProgress, Typography} from "@mui/material";
import {Chart,ArcElement} from 'chart.js';

interface DougnutTimeAtProps {
    username: string;
}

export default function DougnutTimeAt({username}: DougnutTimeAtProps) {
    const {chartData, isLoading, isError} = useAverageTime(username);

    Chart.register(ArcElement)

    if (isLoading) return <CircularProgress/>;
    if (isError || chartData === undefined) {
        return <div>Error</div>
    } else {
        const chart = {
            labels: chartData.key,
            datasets: [{
                label: 'Average time spent at location',
                data: chartData.value
            }]
        }

        return <div>
            <Typography variant="h5">Average time spent per location ({username})</Typography>
            <Doughnut data={chart}/>
        </div>
    }
};
