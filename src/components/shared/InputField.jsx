function InputField({
	label,
	id,
	type,
	errors,
	register,
	required,
	message,
	className,
	min,
	value,
	placeholder,
}) {
	return (
		<div className="flex flex-col w-full gap-1">
			<label
				htmlFor={id}
				className={`${className ? className : ""} font-semibold text-sm text-slate-800`}
			>
				{label}
			</label>
			<input
				type={type}
				id={id}
				placeholder={placeholder}
				className={`${
					className ? className : ""
				} border rounded-md outline-none bg-transparent text-slate-800 px-2 py-2 ${
					errors[id]?.message ? "border-rose-500" : "border-slate-700"
				}`}
				{...register(id, {
					required: { value: required, message },
					minLength: min ? { value: min, message: `Minumum of ${min} characters is required` } : null,
					pattern:
						type === "email"
							? { value: /^[a-zA-Z0-9]+@(?:[a-zA-Z0-9]+\.)+com+$/, message: "Invalid email" }
							: type === "url"
							? {
									value: /^(https?:\/\/)?([\da-z\.-]+)\.([a-z\.]{2,6})([\/\w \.-]*)*\/?$/,
									message: "Please enter a valid URL",
							  }
							: null,
				})}
			/>

			{errors[id]?.message && (
				<span className="text-sm font-semibold text-rose-600">{errors[id]?.message}</span>
			)}
		</div>
	);
}

export default InputField;
