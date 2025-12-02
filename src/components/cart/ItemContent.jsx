import { useState } from "react";
import truncateString from "../../utils/truncateString";
import { HiOutlineTrash } from "react-icons/hi2";
import SetQuantity from "./SetQuantity";
import { useDispatch } from "react-redux";
import { removeItemFromCart, updateCartQuantity } from "../../store/actions";
import toast from "react-hot-toast";
import formatPrice from "../../utils/formatPrice";

function ItemContent({ cartItem, cartId }) {
	const { productId, productName, description, image, quantity, price, discount, specialPrice } =
		cartItem;
	const [currQuantity, setCurrQuantity] = useState(quantity);
	const dispatch = useDispatch();
	const handleQtyIncrease = (cartItem) => {
		dispatch(updateCartQuantity(cartItem, 1, currQuantity, setCurrQuantity, toast));
	};
	const handleQtyDecrease = (cartItem) => {
		dispatch(updateCartQuantity(cartItem, -1, currQuantity, setCurrQuantity, toast));
	};
	const handleRemoveItemFromCart = (cartItem) => {
		dispatch(removeItemFromCart(cartItem, toast));
	};
	return (
		<div className="grid grid-cols-4 md:grid-cols-5 items-center border border-slate-200 rounded-md text-sm md:text-md gap-4 lg:px-4 py-4 p-2">
			<div className="flex flex-col md:col-span-2 justify-self-start gap-2 ">
				<div className="flex md:flex-row flex-col items-start gap-0 sm:gap-3 lg:gap-4">
					<h3 className="text-sm lg:text-lg font-semibold text-slate-600">
						{truncateString(productName, 50)}
					</h3>
				</div>

				<div className="w-12 sm:24 md:w-36">
					<img src={image} alt={productName} className="h-12 sm:h-24 md:h-36 object-cover rounded-md" />
				</div>

				<div className="flex items-start gap-5 mt-3">
					<button
						onClick={() => handleRemoveItemFromCart(cartItem)}
						className="flex items-center font-semibold space-x-2 pl-1 pr-2 py-1 text-xs text-rose-600 border border-rose-600 rounded-md hover:bg-red-200 hover:text-rose-700 hover:cursor-pointer transition-colors duration-100"
					>
						<HiOutlineTrash size={18} className="mr-1" />
						Remove
					</button>
				</div>
			</div>

			<div className="justify-self-center text-sm lg:text-lg text-slate-600 font-semibold">
				{formatPrice(Number(specialPrice))}
			</div>

			<div className="justify-self-center text-sm lg:text-lg text-slate-600 font-semibold">
				<SetQuantity
					quantity={currQuantity}
					cardCounter={true}
					handleQtyIncrease={() => handleQtyIncrease(cartItem)}
					handleQtyDecrease={() => handleQtyDecrease(cartItem)}
				/>
			</div>

			<div className="justify-self-center text-sm lg:text-lg text-slate-600 font-semibold">
				{formatPrice(Number(currQuantity) * Number(specialPrice))}
			</div>
		</div>
	);
}

export default ItemContent;
