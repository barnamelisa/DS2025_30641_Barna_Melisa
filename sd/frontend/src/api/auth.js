// src/api/auth.js
import axios from 'axios';

const API_URL = "http://localhost:8088/api/auth";

export const login = (data) => axios.post(`${API_URL}/login`, data);
