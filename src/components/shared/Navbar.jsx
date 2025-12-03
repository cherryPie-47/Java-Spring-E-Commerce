import { Badge } from "@mui/material";
import { FaStore } from "react-icons/fa";
import { Link, useLocation, useNavigate } from "react-router-dom";
import { LuShoppingCart } from "react-icons/lu";
import { FaSignInAlt } from "react-icons/fa";
import { RxCross2 } from "react-icons/rx";
import { IoIosMenu } from "react-icons/io";
import { useSelector } from "react-redux";
import { useState } from "react";
import UserMenu from "../UserMenu";

function Navbar() {
	const pathname = useLocation().pathname;
	const [navbarOpen, setNavbarOpen] = useState(false);
	const totalCartItems = useSelector((state) => state.carts?.cart?.length) || 0;
	const { user } = useSelector((state) => state.auth);
	return (
		<div className="h-[70px] bg-custom-gradient text-white z-50 flex items-center sticky top-0">
			<div className="lg:px-14 sm:px-8 px-4 w-full flex justify-between">
				<Link to="/" className="flex items-center text-2xl font-bold">
					<FaStore className="mr-2 text-3xl" />
					<span className="font-[Poppins]">E-Shop</span>
				</Link>

				<ul
					className={`flex sm:gap-10 gap-6 sm:items-center sm:static absolute left-0 top-[70px] sm:shadow-none shadow-md
                ${
																	navbarOpen ? "h-fit sm:pb-0 pb-5" : "h-0 overflow-hidden"
																} transition-all duration-100 sm:h-fit sm:bg-none bg-custom-gradient 
                text-white sm:w-fit w-full sm:flex-row flex-col sm:px-0 px-4 `}
				>
					<li className="font-medium transition-all duration-150 ">
						<Link
							className={`${pathname === "/" ? "text-gray-400 " : "text-white"} font-semibold`}
							to="/"
						>
							Home
						</Link>
					</li>

					<li>
						<Link
							className={`${pathname === "/products" ? "text-gray-400" : "text-white"} font-semibold`}
							to="/products"
						>
							Products
						</Link>
					</li>

					<li>
						<Link
							className={`${pathname === "/about" ? "text-gray-400" : "text-white"} font-semibold`}
							to="/about"
						>
							About
						</Link>
					</li>

					<li>
						<Link
							className={`${pathname === "/contact" ? "text-gray-400" : "text-white"} font-semibold`}
							to="/contact"
						>
							Contact
						</Link>
					</li>

					<li>
						<Link
							className={`${pathname === "/cart" ? "text-gray-400" : "text-white"} font-semibold`}
							to="/cart"
						>
							<Badge
								showZero
								badgeContent={totalCartItems}
								color="primary"
								overlap="circular"
								max={9}
								anchorOrigin={{ vertical: "top", horizontal: "right" }}
							>
								<LuShoppingCart size={26} />
							</Badge>
						</Link>
					</li>

					{user && user.id ? (
						<li className="font-semibold transition-all duration-150">
							<UserMenu />
						</li>
					) : (
						<li>
							<Link
								className="flex items-center space-x-2 px-4 py-1.5 bg-button-gradient
                            from-purple-600 to-red-500 text-white font-semibold rounded-md shadow-lg 
                            hover:from-purple-500 hover:to-red-400 transition duration-300 ease-in-out transform"
								to="/login"
							>
								<FaSignInAlt />
								<span>Login</span>
							</Link>
						</li>
					)}
				</ul>
				<button
					onClick={() => setNavbarOpen(!navbarOpen)}
					className="sm:hidden flex items-center sm:mt-0 mt-2"
				>
					{navbarOpen ? (
						<RxCross2 className="text-white text-3xl active:text-gray-400" />
					) : (
						<IoIosMenu className="text-white text-3xl active:text-gray-400 " />
					)}
				</button>
			</div>
		</div>
	);
}

export default Navbar;
