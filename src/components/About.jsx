import ProductCard from "./shared/ProductCard";

const products = [
	{
		image: "https://placehold.co/600x400",
		productName: "iPhone 16 Pro Max",
		description:
			"Experience the latest Apple's flagship with advanced cameras, powerful processors and an all-day battery",
		price: 1450.0,
		specialPrice: 1232.5,
	},
	{
		image: "https://placehold.co/600x400",
		productName: "Samsung Galaxy S21",
		description:
			"Experience the brilliance of the Samsung Galaxy S21 with its vibrant AMOLED display, powerful camera, and sleek design that fits perfectly in your hand.",
		price: 799,
		specialPrice: 699,
	},
	{
		image: "https://placehold.co/600x400",
		productName: "Google Pixel 6",
		description:
			"The Google Pixel 6 boasts cutting-edge AI features, exceptional photo quality, and a stunning display, making it a perfect choice for Android enthusiasts.",
		specialPrice: 400,
		price: 599,
	},
];

function About() {
	return (
		<div className="max-w-7xl mx-auto px-4 py-8">
			<h1 className="text-slate-800 text-4xl font-bold text-center">About Us</h1>
			<div className="flex flex-col lg:flex-row justify-between items-center mb-12">
				<div className="w-full md:w-1/2 text-center md:text-left">
					<p className="text-lg mb-4 ">
						Welcome to our e-commerce store! We are dedicated to providing the best products and services
						to our customer. Our mission is to offer a seamless shopping experience while ensuring the
						highest quality of our offerings.
					</p>
				</div>

				<div className="w-full md:w-1/2 mb-6 md:mb-0">
					<img
						src="https://placehold.co/600x400"
						alt="about us"
						className="w-full h-auto rounded-lg shadow-lg transform transition-transform duration-300 hover:scale-105"
					/>
				</div>
			</div>

			<div className="py-7 space-y-8">
				<h1 className="text-slate-800 text-4xl font-bold text-center">Our Products</h1>
				<div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
					{products.map((p, i) => (
						<ProductCard key={i} product={p} about />
					))}
				</div>
			</div>
		</div>
	);
}

export default About;
