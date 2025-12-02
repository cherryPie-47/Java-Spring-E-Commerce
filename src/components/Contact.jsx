import { FaEnvelope, FaMapMarkedAlt, FaPhone } from "react-icons/fa";
import ContactImg from "../assets/sliders/contact_img.jpeg";

function Contact() {
	return (
		<div className="relative min-h-screen">
			<div
				className="absolute inset-0 bg-cover bg-center"
				style={{
					backgroundImage:
						"linear-gradient(rgba(0, 0, 0, 0.3), rgba(0, 0, 0, 0.6)), url('https://images.pexels.com/photos/317377/pexels-photo-317377.jpeg?_gl=1*1wzbcxa*_ga*NTE3MTkzOTQyLjE3NjQ1OTczNzI.*_ga_8JE65Q40S6*czE3NjQ1OTczNzIkbzEkZzEkdDE3NjQ1OTc0NzMkajU0JGwwJGgw')",
					backgroundSize: "cover",
					backgroundPosition: "center",
				}}
			></div>

			<div className="relative flex flex-col items-center justify-center min-h-screen py-12">
				<div className="bg-white shadow-lg rounded-lg p-8 w-full max-w-lg">
					<h1 className="text-4xl font-bold text-center mb-6">Contact Us</h1>
					<p className="text-gray-600 text-center mb-4">
						We would love to hear from you! Please fill out the form below or contact us directly
					</p>

					<form className="space-y-4" action="">
						<div>
							<label className="block text-sm font-medium text-gray-700" htmlFor="">
								Name
							</label>
							<input
								className="mt-1 block w-full border border-gray-300 rounded-lg p-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
								type="text"
								required
							/>
						</div>

						<div>
							<label className="block text-sm font-medium text-gray-700" htmlFor="">
								Email
							</label>
							<input
								className="mt-1 block w-full border border-gray-300 rounded-lg p-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
								type="email"
								required
							/>
						</div>

						<div>
							<label className="block text-sm font-medium text-gray-700" htmlFor="">
								Message
							</label>
							<textarea
								className="mt-1 block w-full border border-gray-300 rounded-lg p-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
								rows={4}
								required
							/>
						</div>

						<button className="w-full bg-blue-500 text-white py-2 rounded-lg hover:bg-blue-600 transition duration-100">
							Send Message
						</button>
					</form>

					<div className="mt-8 text-center">
						<h2 className="text-lg font-semibold">Contact Information</h2>
						<div className="flex flex-col items-center space-y-2 mt-4">
							<div className="flex items-center">
								<FaPhone className="text-blue-500 mr-2" />
								<span className="text-gray-600">+0 123 456 7890</span>
							</div>

							<div className="flex items-center">
								<FaEnvelope className="text-blue-500 mr-2" />
								<span className="text-gray-600">cherryPie-47@gmail.com</span>
							</div>

							<div className="flex items-center">
								<FaMapMarkedAlt className="text-blue-500 mr-2" />
								<span className="text-gray-600">123 ABC Street, DEF Ward, Ho Chi Minh City, Vietnam</span>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	);
}

export default Contact;
