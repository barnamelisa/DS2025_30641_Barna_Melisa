// src/api/users.js (CORECT)
import axios from 'axios';
const API_URL = "http://localhost:8088/api/users";

export const getUsers = () => axios.get(API_URL);
// 🚀 CORECȚIE: primește user (care este deja payload-ul complet)
export const createUser = (user) => {
    return axios.post(API_URL, user); // Trimite direct obiectul
};
export const updateUser = (id, user) => axios.put(`${API_URL}/${id}`, user);
export const deleteUser = (id) => axios.delete(`${API_URL}/${id}`);