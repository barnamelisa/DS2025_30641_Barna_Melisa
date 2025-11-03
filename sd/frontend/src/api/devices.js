// src/api/devices.js
import axios from 'axios';
const API_URL = "http://localhost:8088/api/devices";

export const getDevices = () => axios.get(API_URL);
export const createDevice = (device) => axios.post(API_URL, device);
export const updateDevice = (id, device) => axios.put(`${API_URL}/${id}`, device);
export const deleteDevice = (id) => axios.delete(`${API_URL}/${id}`);