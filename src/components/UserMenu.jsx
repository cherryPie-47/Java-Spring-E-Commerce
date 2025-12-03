import { Avatar } from "@mui/material";
import Button from "@mui/material/Button";
import Menu from "@mui/material/Menu";
import MenuItem from "@mui/material/MenuItem";
import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { BiUser } from "react-icons/bi";
import { useDispatch, useSelector } from "react-redux";
import { LuShoppingCart } from "react-icons/lu";
import { IoExitOutline } from "react-icons/io5";
import { logoutUser } from "../store/actions";

function UserMenu() {
	const [anchorEl, setAnchorEl] = useState(null);
	const dispatch = useDispatch();
	const navigate = useNavigate();
	const open = Boolean(anchorEl);
	const handleClick = (event) => {
		setAnchorEl(event.currentTarget);
	};
	const handleClose = () => {
		setAnchorEl(null);
	};
	const handleLogout = () => {
		dispatch(logoutUser(navigate));
	};
	const { user } = useSelector((state) => state.auth);

	return (
		<div>
			<div
				onClick={handleClick}
				className="flex flex-row items-center rounded-full text-slate-700 sm:border sm:border-slate-700 gap-1 cursor-pointer hover: shadow-md transition duration-150"
			>
				<Avatar alt="menu" src="" />
			</div>

			<Menu
				id="basic-menu"
				anchorEl={anchorEl}
				open={open}
				onClose={handleClose}
				slotProps={{
					list: {
						"aria-labelledby": "basic-button",
					},
				}}
			>
				<Link to="/profile">
					<MenuItem onClick={handleClose} className="flex gap-2">
						<BiUser size={20} />
						<span className="text-[16px] font-bold mt-1">{user?.username}</span>
					</MenuItem>
				</Link>

				<Link to="/profile/orders">
					<MenuItem onClick={handleClose} className="flex gap-2">
						<LuShoppingCart size={16} />
						<span className="text-[16px] font-semibold ml-1">Order</span>
					</MenuItem>
				</Link>

				<MenuItem onClick={handleLogout} className="flex gap-2">
					<div className="w-full flex items-center text-white font-semibold rounded-sm bg-button-gradient px-4 py-2 gap-2">
						<IoExitOutline size={20} className="mb-[3px]" />
						<span className="text-[16px] font-semibold">Logout</span>
					</div>
				</MenuItem>
			</Menu>
		</div>
	);
}

export default UserMenu;
