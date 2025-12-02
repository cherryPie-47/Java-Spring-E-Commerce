import { useDispatch, useSelector } from "react-redux";
import ProductCard from "../shared/ProductCard";
import HeroBanner from "./Hero Banner";
import { fetchProducts } from "../../store/actions";
import { useEffect } from "react";
import Loader from "../shared/Loader";
import { FaExclamationTriangle } from "react-icons/fa";

function Home() {
	const dispatch = useDispatch();
	const { products } = useSelector((state) => state.products);
	const { productsIsLoading, productsErrorMessage } = useSelector((state) => state.errors);

	useEffect(() => {
		dispatch(fetchProducts());
	}, [dispatch]);

	return (
		<div className="lg:px-14 sm:px-8 px-4">
			<div className="py-6">
				<HeroBanner />
			</div>

			<div className="py-5">
				<div className="flex flex-col justify-center items-center space-y-2">
					<h1 className="text-slate-800 text-4xl font-bold">Products</h1>
					<span className="text-slate-700 text-xl">
						Discover our handpicked selection of top-rated items just for you!
					</span>
				</div>
			</div>

			{productsIsLoading ? (
				<Loader />
			) : productsErrorMessage ? (
				<div className="flex justify-center items-center h-[200px]">
					<FaExclamationTriangle className="text-slate-800 text-3xl mr-2" />
					<span className="text-slate-800 text-lg font-medium">{productsErrorMessage}</span>
				</div>
			) : (
				<div className="pb-6 pt-14 grid 2xl:grid-cols-4 lg:grid-cols-3 sm:grid-cols-2 gap-y-6 gap-x-6">
					{products &&
						products.slice(0, 8).map((product, i) => <ProductCard key={i} product={product} />)}
				</div>
			)}
		</div>
	);
}

export default Home;
