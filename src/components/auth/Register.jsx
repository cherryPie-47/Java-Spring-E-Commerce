import { useState } from "react";
import { useForm } from "react-hook-form";
import { FaUserPlus } from "react-icons/fa";
import { useDispatch } from "react-redux";
import { Link, useNavigate } from "react-router-dom";
import InputField from "../shared/InputField";
import { registerUser } from "../../store/actions";
import toast from "react-hot-toast";

function Register() {
	const navigate = useNavigate();
	const [loader, setLoader] = useState(false);
	const dispatch = useDispatch();

	const {
		register,
		handleSubmit,
		reset,
		formState: { errors },
	} = useForm({ mode: "onTouched" });

	const handleRegister = async (data) => {
		dispatch(registerUser(data, reset, navigate, setLoader, toast));
	};
	return (
		<div className="min-h-[calc(100vh-64px)] flex justify-center items-center">
			<form
				action=""
				onSubmit={handleSubmit(handleRegister)}
				className="sm:w-[450px] w-[360px] shadow-custom px-4 sm:px-8 py-8 rounded-md"
			>
				<div className="flex flex-col items-center justify-center space-y-4">
					<FaUserPlus className="text-slate-800" size={48} />
					<h1 className="text-slate-800 text-center font-montserrat text-2xl lg:text-3xl font-bold ">
						Register Here
					</h1>
				</div>

				<hr className="mt-2 mb-5 text-slate-300" />

				<div className="flex flex-col gap-4">
					<InputField
						label={"Username"}
						required
						id={"username"}
						type="text"
						register={register}
						errors={errors}
						message="*Username is required"
						placeholder="Enter your username"
						min={4}
					/>

					<InputField
						label={"Email"}
						required
						id={"email"}
						type="email"
						register={register}
						errors={errors}
						message="*Email is required"
						placeholder="Enter your email"
						min={4}
					/>

					<InputField
						label={"Password"}
						required
						id={"password"}
						type="password"
						register={register}
						errors={errors}
						message="*Password is required"
						placeholder="Enter your password"
						min={4}
					/>
				</div>

				<button
					disabled={loader}
					type="submit"
					className="flex items-center justify-center w-full text-white font-semibold bg-button-gradient rounded-sm gap-2 py-2 my-6 hover:text-slate-400 transition-colors duration-150 hover:cursor-pointer"
				>
					{loader ? (
						<>
							<Spinner />
							Loading...
						</>
					) : (
						"Register"
					)}
				</button>

				<p className="text-center text-sm text-slate-600 mt-6">
					Already have an account?
					<Link to="/login" className="font-semibold underline hover:text-slate-900 ml-2">
						<span>Login</span>
					</Link>
				</p>
			</form>
		</div>
	);
}

export default Register;
