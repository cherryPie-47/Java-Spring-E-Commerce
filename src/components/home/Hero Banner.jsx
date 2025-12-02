import { Swiper, SwiperSlide } from "swiper/react";
import { bannersList } from "../../utils";
import { Pagination, EffectFade, Navigation, Autoplay } from "swiper/modules";

import "swiper/css"; // Core Swiper styles
import "swiper/css/navigation"; // Styles for the navigation arrows
import "swiper/css/pagination"; // Styles for the pagination dots
import "swiper/css/effect-fade"; // Styles for the fade effect
import { Link } from "react-router-dom";
function HeroBanner() {
	const colors = ["bg-banner-1", "bg-banner-2", "bg-banner-3", "bg-banner-4"];

	return (
		<div className="py-2 rounded-md">
			<Swiper
				grabCursor={true}
				// autoplay={{ delay: 5000, disableOnInteraction: false }}
				navigation
				modules={[Pagination, EffectFade, Navigation, Autoplay]}
				pagination={{ clickable: true }}
				scrollbar={{ draggable: true }}
				spaceBetween={50}
				slidesPerView={1}
			>
				{bannersList.map((b, i) => (
					<SwiperSlide>
						<div className={`rounded-md sm:h-[500px] h-96 bg-banner-${i + 1}`}>
							<div className="flex items-center justify-center">
								<div className="hidden lg:flex justify-center w-1/2 p-8">
									<div className="text-center">
										<h3 className="text-3xl text-white font-bold">{b.title}</h3>
										<h1 className="text-5xl text-white font-bold mt-2">{b.subtitle}</h1>
										<p className="text-white font-bold">{b.description}</p>
										<Link
											to="/products"
											className="mt-6 inline-block bg-gray-900 text-white py-2 px-4 rounded hover:bg-gray-700 transition-colors duration-150"
										>
											Shop
										</Link>
									</div>
								</div>

								<div className="w-full flex justify-center lg:w-1/2 p-4">
									<img src={b?.image} alt="" />
								</div>
							</div>
						</div>
					</SwiperSlide>
				))}
			</Swiper>
		</div>
	);
}

export default HeroBanner;
