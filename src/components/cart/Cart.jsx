import { MdArrowBack, MdShoppingCart } from "react-icons/md";
import { Link } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import ItemContent from "./ItemContent";
import CartEmpty from "./CartEmpty";
import formatPrice from "../../utils/formatPrice";

function Cart() {
	const dispatch = useDispatch();
	const { cart, cartId } = useSelector((state) => state.carts);
	const newCart = { ...cart };

	newCart.totalPrice = cart?.reduce(
		(acc, cur) => acc + Number(cur?.specialPrice) * Number(cur?.quantity),
		0
	);

	if (!cart || cart.length === 0) {
		return <CartEmpty />;
	}

	return (
		<div className="lg:px-14 sm:px-8 px-4 py-10">
			<div className="flex flex-col items-center mb-12">
				<h1 className="text-4xl font-bold text-slate-800 flex items-center gap-3">
					<MdShoppingCart size={36} className="text-slate-800" />
					Your Cart
				</h1>
				<p className="text-xl text-slate-600 mt-2">All your selected items</p>
			</div>

			<div className="grid md:grid-cols-5 grid-cols-4 gap-4 pb-2 font-semibold ">
				<div className="md:col-span-2 justify-self-start text-xl text-slate-800 lg:ps-4">Product</div>
				<div className="justify-self-center text-xl text-slate-800">Price</div>
				<div className="justify-self-center text-xl text-slate-800">Quantity</div>
				<div className="justify-self-center text-xl text-slate-800">Total</div>
			</div>

			<div>
				{cart &&
					cart.length > 0 &&
					cart.map((cartItem, i) => <ItemContent key={i} cartItem={cartItem} cartId={cartId} />)}
			</div>

			<div className="border-t-[1.5px] border-slate-300 py-4 flex flex-col sm:flex-row px-2 sm:px-0 sm:justify-between gap-4">
				<div></div>
				<div className="flex flex-col text-sm gap-1">
					<div className="flex justify-between w-full text-sm md:text-lg font-semibold">
						<span>Subtotal</span>
						<span>{formatPrice(newCart?.totalPrice)}</span>
					</div>

					<p className="text-slate-500 text-sm">Taxes and shipping calculated at checkout</p>

					<Link className="flex justify-end w-full" to="/checkout">
						<button
							onClick={() => {}}
							className="flex justify-center items-center text-sm text-white font-semibold px-4 py-2 w-[300px] rounded-md bg-blue-500 gap-2 hover:text-gray-300 hover:cursor-pointer transition duration-100 "
						>
							<MdShoppingCart size={20} />
							Check out
						</button>
					</Link>

					<Link
						className="flex items-center text-slate-500 mt-2 gap-2 hover:text-slate-700"
						to="/products"
					>
						<MdArrowBack size={18} />
						<span>Continue Shopping</span>
					</Link>
				</div>
			</div>
		</div>
	);
}

export default Cart;
