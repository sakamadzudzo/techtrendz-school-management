import React from "react";
import { Line } from "react-chartjs-2";
import { Chart as ChartJS, CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend } from "chart.js";

ChartJS.register(CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend);

interface LineChartProps {
  labels: string[];
  datasets: { label: string; data: number[]; borderColor?: string; backgroundColor?: string }[];
  height?: number;
}

export default function LineChart({ labels, datasets, height = 300 }: LineChartProps) {
  const data = {
    labels,
    datasets: datasets.map((ds) => ({
      ...ds,
      borderColor: ds.borderColor || "rgba(59, 130, 246, 1)",
      backgroundColor: ds.backgroundColor || "rgba(59, 130, 246, 0.1)",
      tension: 0.4,
    })),
  };

  const options = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
      legend: { position: "top" as const },
      tooltip: { mode: "index" as const, intersect: false },
    },
    scales: {
      y: { beginAtZero: true },
    },
  };

  return <Line data={data} options={options} height={height} />;
}
