const btnStyle =
	"border-[1.2px] border-slate-800 rounded px-4 py-0.5 w-12 hover:cursor-pointer transition-colors duration-150 hover:bg-slate-300";

function SetQuantity({ quantity, cardCounter, handleQtyIncrease, handleQtyDecrease }) {
	return (
		<div className="flex items-center gap-8">
			{cardCounter ? null : <div className="font-semibold">QUANTITY</div>}
			<div className="flex flex-col md:flex-row items-center gap-4 text-sm lg:text-[22px]">
				<button disabled={quantity <= 1} className={btnStyle} onClick={handleQtyDecrease}>
					-
				</button>

				<div className="text-rose-500 text-lg">{quantity}</div>

				<button className={btnStyle} onClick={handleQtyIncrease}>
					+
				</button>
			</div>
		</div>
	);
}

export default SetQuantity;
