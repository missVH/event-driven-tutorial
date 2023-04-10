import {CircularProgress, Typography} from "@mui/material";
import {Bar} from "react-chartjs-2";
import usePopularTimes from "../../data/usePopularTimes";
import Chart from 'chart.js/auto';
import {CategoryScale} from "chart.js";

export default function BarChartTimes() {
    const {chartData, isLoading, isError} = usePopularTimes();

    Chart.register(CategoryScale);

    if (isLoading) return <CircularProgress/>
    if (isError || chartData === undefined) {
        return <div>Error</div>;
    } else {
        const data = {
            labels: chartData.key,
            datasets: [{
                label: 'Visits',
                data: chartData.value,
                backgroundColor: "#5CCEFF",
            }]
        }

        const options = {
            responsive: true,
            plugins: {
                legend: {
                    position: 'top' as const,
                },
                title: {
                    display: true,
                    text: 'Visits/Hour',
                },
            },

        };

        return <div>
            <Typography variant="h5">Popular times (all locations)</Typography>
            <Bar className="myBar" options={options} data={data}/>
        </div>
    }
}
