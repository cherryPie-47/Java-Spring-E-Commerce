const initialState = {
	user: null,
	address: [],
};

const authReducer = (state = initialState, action) => {
	switch (action.type) {
		case "LOGIN_USER":
			return {
				...state,
				user: action.payload,
			};
		case "LOGOUT_USER":
			return initialState;
		default:
			return state;
	}
};

export default authReducer;
