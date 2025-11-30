import { Description, Dialog, DialogPanel, DialogTitle, DialogBackdrop } from "@headlessui/react";
import { useState } from "react";
import { Divider } from "@mui/material";
import { MdDone, MdClose } from "react-icons/md";
import Status from "./Status";

function ProductViewModal({ isOpen, setOpen, product, isAvailable }) {
	const { productId, productName, image, description, quantity, price, discount, specialPrice } =
		product;
	function open() {
		setOpen(true);
	}
	function close() {
		setOpen(false);
	}

	return (
		<>
			<Dialog open={isOpen} onClose={close} className="relative z-10">
				<DialogBackdrop className="fixed inset-0 bg-gray-500/50 transition-opacity" />
				<div className="fixed inset-0 flex w-screen items-center justify-center p-4">
					<DialogPanel className="max-w-2xl min-w-2xl w-full space-y-4 bg-white relative overflow-hidden rounded-lg shadow-xl transition-all ">
						{image && (
							<div className="flex justify-center aspect-3/2">
								<img src={image} alt={productName} />
							</div>
						)}
						<div className="px-10 pt-6 pb-4">
							<DialogTitle className="text-xl sm:text-2xl lg:text-3xl font-semibold leading-6 text-gray-800 mb-4">
								{productName}
							</DialogTitle>

							<div className="space-y-2 text-gray-700 pb-4">
								<div className="flex items-center justify-between gap-2">
									{specialPrice ? (
										<div>
											<div className="flex flex-col items-center">
												<span className="text-gray-400 line-through">${Number(price).toFixed(2)}</span>
												<span className="text-l font-semibold text-slate-700">
													${Number(specialPrice).toFixed(2)}
												</span>
											</div>
										</div>
									) : (
										<div className="flex flex-col">
											<span className="text-l font-bold text-slate-700">
												{"   "}${Number(price).toFixed(2)}
											</span>
										</div>
									)}
									{isAvailable ? (
										<Status
											text={"In Stock"}
											icon={MdDone}
											bg={"bg-teal-200"}
											color={"text-teal-900"}
										></Status>
									) : (
										<Status
											text={"Out of Stock"}
											icon={MdClose}
											bg={"bg-rose-200"}
											color={"text-rose-900"}
										></Status>
									)}
								</div>
							</div>

							<Divider />

							<Description className="text-l text-gray-600 pt-2">{description}</Description>
							<div className="py-4 flex justify-end gap-4">
								<button
									onClick={close}
									className="px-4 py-2 text-sm font-semibold text-slate-700 border rounded-md hover:bg-slate-200 hover:cursor-pointer transition-color transform duration-200"
								>
									Close
								</button>
							</div>
						</div>
					</DialogPanel>
				</div>
			</Dialog>
		</>
	);
}

export default ProductViewModal;
