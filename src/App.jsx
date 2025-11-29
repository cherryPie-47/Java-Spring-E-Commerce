import { useState } from "react";
import "./App.css";
import Products from "./components/Products";

function App() {
	return (
		<>
			<h1 className="text-white bg-gray-600 rounded-xl px-4 py-2 w-fit">Hello, Tailwind!</h1>
			<Products />
		</>
	);
}

export default App;
