import { MdArrowBack, MdShoppingCart } from "react-icons/md";
import { Link } from "react-router-dom";

function CartEmpty() {
	return (
		<div className="min-h-[800px] flex flex-col items-center justify-center">
			<div className="flex flex-col items-center">
				<MdShoppingCart size={80} className="mb-4 text-slate-500" />
				<h1 className="text-3xl font-bold text-slate-700">Your cart is empty</h1>
				<h1 className="text-lg font-semibold text-slate-500">Add some products to get started</h1>
			</div>

			<div className="mt-6">
				<Link
					to="/"
					className="flex items-center text-blue-500 gap-2 hover:text-blue-600 transition duration-100"
				>
					<MdArrowBack />
					<span className="font-medium">Start Shopping</span>
				</Link>
			</div>
		</div>
	);
}

export default CartEmpty;
