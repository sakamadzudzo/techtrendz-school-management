import React from "react";
import { Bar } from "react-chartjs-2";
import { Chart as ChartJS, CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend } from "chart.js";

ChartJS.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend);

interface BarChartProps {
  labels: string[];
  datasets: { label: string; data: number[]; backgroundColor?: string; borderColor?: string }[];
  height?: number;
}

export default function BarChart({ labels, datasets, height = 300 }: BarChartProps) {
  const data = {
    labels,
    datasets: datasets.map((ds) => ({
      ...ds,
      backgroundColor: ds.backgroundColor || "rgba(59, 130, 246, 0.5)",
      borderColor: ds.borderColor || "rgba(59, 130, 246, 1)",
      borderWidth: 1,
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

  return <Bar data={data} options={options} height={height} />;
}
