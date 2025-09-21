import { useAuth0 } from "@auth0/auth0-react";
import React, { useEffect, useState } from "react";

const Dashboard = () => {
    const { user, isAuthenticated, getAccessTokenSilently } = useAuth0();

    const [activities, setActivities] = useState([]);
    const [loading, setLoading] = useState(true);

    const [formData, setFormData] = useState({
        ip: '',
        country: '',
        city: '',
    });

    const [searchFormData, setSearchFormData] = useState({
        category: 'ip',
        text: ''
    });

    const [deletingId, setDeletingId] = useState(null);

    const handleDelete = async (activityId) => {
        if (!window.confirm('Are you sure you want to delete this activity?')) {
            return;
        }

        setDeletingId(activityId);

        try {
            const response = await fetch(`http://localhost:8080/spring-rest-api/api/activity/${activityId}`, {
                method: "DELETE",
                headers: {
                    "Content-Type": "application/json",
                },
            });

            if (!response.ok) {
                throw new Error("Failed to delete activity");
            }
            
            setActivities(prevActivities => 
                prevActivities.filter(activity => activity.id !== activityId)
            );
        } catch (error) {
            console.error("Error deleting activity:", error);
            alert("Failed to delete activity. Please try again.");
        } finally {
            setDeletingId(null);
        }
    };

    const handleListAll = async () => {
        setLoading(true);
        try {
            const token = await getAccessTokenSilently();
            const response = await fetch("http://localhost:8080/spring-rest-api/api/activity", {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            if (!response.ok) {
                throw new Error("Failed to fetch all activities");
            }
            const data = await response.json();
            setActivities(data);
            // Clear search form if it exists
            if (setSearchFormData) {
                setSearchFormData({ category: 'ip', text: '' });
            }
        } catch (error) {
            console.error("Error fetching all activities:", error);
            alert("Failed to fetch activities. Please try again.");
        } finally {
            setLoading(false);
        }
    };

    const handleSearch = async (e) => {
        e.preventDefault();

        if (!searchFormData.category || !searchFormData.text) {
            alert("Please select a category and enter search text.");
            return;
        }

        setLoading(true);
        try {
            const token = await getAccessTokenSilently();
            const response = await fetch(`http://localhost:8080/spring-rest-api/api/activity/search?category=${encodeURIComponent(searchFormData.category)}&text=${encodeURIComponent(searchFormData.text)}`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            if (!response.ok) {
                throw new Error("Failed to search activities");
            }
            const data = await response.json();
            setActivities(data);
        } catch (error) {
            console.error("Error searching activities:", error);
            alert("Failed to search activities. Please try again.");
        } finally {
            setLoading(false);
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            const token = await getAccessTokenSilently();
            const response = await fetch("http://localhost:8080/spring-rest-api/api/activity", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${token}`
                },
                body: JSON.stringify({ ip: formData.ip, country: formData.country, city: formData.city }),
            });

            if (!response.ok) {
                throw new Error("Failed to add activity");
            }

            const data = await response.json();
            alert("Activity added successfully!");
            // Clear the form
            setFormData({ ip: '', country: '', city: '' });
        } catch (error) {
            console.error(error);
        }
    };

    useEffect(() => {
        handleListAll();
    }, []);

    return (
        <div>
            <div className="p-6">
                <h2 className="text-3xl font-bold text-gray-800 mb-6">📊 Dashboard</h2>

                {isAuthenticated && user && (
                    <div className="bg-white shadow-md rounded-lg p-6 flex items-center gap-4">
                        <div className="w-14 h-14 flex items-center justify-center bg-blue-500 text-white rounded-full text-xl font-semibold">
                            {user.name?.charAt(0).toUpperCase()}
                        </div>
                        <div>
                            <h3 className="text-xl font-semibold text-gray-700">
                                Welcome, <span className="text-blue-600">{user.name}</span> 🎉
                            </h3>
                            <p className="text-gray-500">Your email: {user.email}</p>
                        </div>
                    </div>
                )}
            </div>


            <div className="p-6">

                <form
                    onSubmit={handleSubmit}
                    className="flex flex-wrap items-center gap-3 bg-white shadow-md rounded-lg mb-6 p-6"
                >
                    <input
                        type="text"
                        placeholder="IP"
                        value={formData.ip}
                        onChange={(e) => setFormData({ ...formData, ip: e.target.value })}
                        className="flex-1 border border-gray-300 focus:ring-2 focus:ring-blue-400 focus:border-blue-400 px-3 py-2 rounded-lg outline-none transition"
                        required
                    />
                    <input
                        type="text"
                        placeholder="Country"
                        value={formData.country}
                        onChange={(e) => setFormData({ ...formData, country: e.target.value })}
                        className="flex-1 border border-gray-300 focus:ring-2 focus:ring-blue-400 focus:border-blue-400 px-3 py-2 rounded-lg outline-none transition"
                        required
                    />
                    <input
                        type="text"
                        placeholder="City"
                        value={formData.city}
                        onChange={(e) => setFormData({ ...formData, city: e.target.value })}
                        className="flex-1 border border-gray-300 focus:ring-2 focus:ring-blue-400 focus:border-blue-400 px-3 py-2 rounded-lg outline-none transition"
                        required
                    />
                    <button
                        type="submit"
                        className="bg-blue-500 text-white px-5 py-2 rounded-lg shadow hover:bg-blue-600 focus:ring-2 focus:ring-blue-400 transition"
                    >
                        ➕ Add Activity
                    </button>
                </form>

                <form onSubmit={handleSearch} className="flex items-center gap-3 bg-white shadow-md rounded-lg p-6">
                    
                    <select
                        className="border border-gray-300 rounded-lg px-3 py-2 focus:ring-2 focus:ring-blue-400 focus:border-blue-400 outline-none transition"
                        // defaultValue="ip"
                        value={searchFormData.category}
                        onChange={(e) => setSearchFormData({ ...searchFormData, category: e.target.value })}
                    >
                        <option value="ip">IP</option>
                        <option value="country">Country</option>
                        <option value="city">City</option>
                    </select>

                    
                    <input
                        type="text"
                        placeholder="Search activities..."
                        value={searchFormData.text}
                        onChange={(e) => setSearchFormData({ ...searchFormData, text: e.target.value })}
                        className="flex-1 border border-gray-300 px-3 py-2 rounded-lg focus:ring-2 focus:ring-blue-400 focus:border-blue-400 outline-none transition"
                    
                    />

                   
                    <button
                        type="submit"
                        className="bg-blue-500 text-white px-5 py-2 rounded-lg shadow hover:bg-blue-600 focus:ring-2 focus:ring-blue-400 transition"
                    >
                        🔍 Search
                    </button>

                    
                    <button
                        type="button"
                        onClick={handleListAll}
                        disabled={loading}
                        className={`px-5 py-2 rounded-lg shadow focus:ring-2 focus:ring-green-400 transition ${
                            loading 
                                ? "bg-gray-400 cursor-not-allowed" 
                                : "bg-green-500 hover:bg-green-600 text-white"
                        }`}
                    >
                        {loading ? "⏳ Loading..." : "📋 List All Data"}
                    </button>
                </form>

            </div>



            <div className="overflow-x-auto p-4">
                <div className="shadow-lg rounded-lg overflow-hidden">
                    <table className="min-w-full bg-white">
                        <thead className="bg-gray-100 sticky top-0">
                            <tr>
                                <th className="py-3 px-6 text-left text-gray-600 font-semibold">ID</th>
                                <th className="py-3 px-6 text-left text-gray-600 font-semibold">IP</th>
                                <th className="py-3 px-6 text-left text-gray-600 font-semibold">Country</th>
                                <th className="py-3 px-6 text-left text-gray-600 font-semibold">City</th>
                                <th className="py-3 px-6 text-center text-gray-600 font-semibold">Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            {loading ? (
                                <tr>
                                    <td colSpan="5" className="text-center py-6 text-gray-500">
                                        Loading...
                                    </td>
                                </tr>
                            ) : activities.length > 0 ? (
                                activities.map((activity) => (
                                    <tr
                                        key={activity.id}
                                        className="hover:bg-gray-50 even:bg-gray-50 transition-colors"
                                    >
                                        <td className="py-3 px-6 border-b">{activity.id}</td>
                                        <td className="py-3 px-6 border-b">{activity.ip}</td>
                                        <td className="py-3 px-6 border-b">{activity.country}</td>
                                        <td className="py-3 px-6 border-b">{activity.city}</td>
                                        <td className="py-3 px-6 border-b text-center">
                                            <button 
                                                onClick={() => handleDelete(activity.id)}
                                                disabled={deletingId === activity.id}
                                                className={`px-3 py-1 rounded-full shadow transition ${
                                                    deletingId === activity.id
                                                        ? "bg-gray-400 cursor-not-allowed"
                                                        : "bg-red-500 hover:bg-red-600 text-white"
                                                }`}
                                            >
                                                {deletingId === activity.id ? "⏳ Deleting..." : "🗑️ Delete"}
                                            </button>
                                        </td>
                                    </tr>
                                ))
                            ) : (
                                <tr>
                                    <td colSpan="5" className="text-center py-6 text-gray-500">
                                        No activities found.
                                    </td>
                                </tr>
                            )}
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    );
};

export default Dashboard;