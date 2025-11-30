import Pagination from "@mui/material/Pagination";
import { useLocation, useNavigate, useSearchParams } from "react-router-dom";

function Paginations({ totalPages }) {
	const [searchParams] = useSearchParams();
	const pathname = useLocation().pathname;
	const navigate = useNavigate();
	const currPage = searchParams.get("page") ? Number(searchParams.get("page")) : 1;

	const handlePageChange = (event, value) => {
		searchParams.set("page", value.toString());
		navigate(`${pathname}?${searchParams.toString()}`);
	};
	return (
		<div>
			<Pagination
				count={totalPages}
				defaultPage={1}
				page={currPage}
				siblingCount={1}
				showFirstButton
				showLastButton
				shape={"rounded"}
				color={"primary"}
				onChange={handlePageChange}
			/>
		</div>
	);
}

export default Paginations;
