import { useState } from "react";
import { TiShoppingCart } from "react-icons/ti";
import ProductViewModal from "./ProductViewModal";

function ProductCard({ product }) {
	const { productId, productName, image, description, quantity, price, discount, specialPrice } =
		product;
	const [openProductViewModal, setOpenProductViewModal] = useState(false);
	const buttonLoader = false;
	// We cannot set the initial value to be null, as ProductViewModal component will use this value to deconstruct product field
	const [selectedViewProduct, setSelectedViewProduct] = useState("");
	const isAvailable = quantity !== 0 ? true : false;

	const handleViewProduct = (product) => {
		setSelectedViewProduct(product);
		setOpenProductViewModal(true);
	};
	return (
		<div className="rounded-lg shadow-xl overflow-hidden transition-shadow duration-300">
			<div
				onClick={() => {
					handleViewProduct(product);
				}}
				className="w-full overflow-hidden aspect-3/2"
			>
				<img
					src={image}
					alt={productName}
					className="w-full h-full cursor-pointer transition-transform duration-200 transform hover:scale-105"
				/>
			</div>
			<div className="p-4">
				<h2
					onClick={() => {
						handleViewProduct(product);
					}}
					className="text-lg font-semibold mb-2 cursor-pointer"
				>
					{productName}
				</h2>

				<div className="min-h-20 max-h-20">
					<p className="text-gray-600 text-sm">{description}</p>
				</div>

				<div className="flex items-center justify-between">
					{specialPrice ? (
						<div>
							<div className="flex flex-col">
								<span className="text-gray-400 line-through">${Number(price).toFixed(2)}</span>
								<span className="text-l font-semibold text-slate-700">
									${Number(specialPrice).toFixed(2)}
								</span>
							</div>
							<div className="flex flex-col"></div>
						</div>
					) : (
						<div className="flex flex-col">
							<span className="text-l font-bold text-slate-700">
								{"   "}${Number(price).toFixed(2)}
							</span>
						</div>
					)}
					<button
						disable={isAvailable.toString() || buttonLoader}
						onClick={() => {}}
						className={`bg-blue-500 ${
							isAvailable ? "opacity-100 hover:bg-blue-600 cursor-pointer" : "opacity-70"
						} 
                    text-white py-2 px-4 rounded-lg items-center transition-colors duration-300 w-36 flex justify-center `}
					>
						<TiShoppingCart className="mr-2" />
						{isAvailable ? "Add to Cart" : "Out of stock"}
					</button>
				</div>
			</div>
			<ProductViewModal
				isOpen={openProductViewModal}
				setOpen={setOpenProductViewModal}
				product={selectedViewProduct}
				isAvailable={isAvailable}
			/>
		</div>
	);
}

export default ProductCard;
