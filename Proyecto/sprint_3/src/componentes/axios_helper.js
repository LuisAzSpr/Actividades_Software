
export const getAuthToken = () => {
    return window.localStorage.getItem("auth_token");
}

export const setAuthToken = (token) => {
    window.localStorage.setItem("auth_token", token)
}
