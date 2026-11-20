import { Routes, Route, Outlet } from "react-router-dom";
import { Box } from "@mui/material";
import Sidebar from "./components/Sidebar";
import Login from "./pages/Login";
import Dashboard from "./pages/Dashboard.jsx";
import NewQuote from "./pages/NewQuote.jsx";
import ProtectedRoute from "./components/ProtectedRoute";
import AdminUsuarios from "./pages/admin/AdminUsuarios.jsx";
import AdminVidrios from "./pages/admin/AdminVidrios.jsx";

const MainLayout = () => {
  return (
    <Box sx={{ display: 'flex' }}>
      <Sidebar />
      <Box
        component="main"
        sx={{
          flexGrow: 1,
          p: 3,
          backgroundColor: '#f9f9f9',
          minHeight: '100vh'
        }}
      >
        <Outlet />
      </Box>
    </Box>
  );
};

function App() {
  return (
    <Routes>
      <Route path="/" element={<Login />} />

      {/* Rutas vendedor */}
      <Route
        element={
          <ProtectedRoute>
            <MainLayout />
          </ProtectedRoute>
        }
      >
        <Route path="/pedidos" element={<Dashboard />} />
        <Route path="/nueva-cotizacion" element={<NewQuote />} />
        <Route path="/nueva-cotizacion/:idQuotation" element={<NewQuote />} />
      </Route>

      {/* Rutas admin */}
      <Route
        element={
          <ProtectedRoute requiredRole="ADMIN">
            <MainLayout />
          </ProtectedRoute>
        }
      >
        <Route path="/admin/pedidos" element={<Dashboard isAdmin={true} />} />
        {/* Comentario JSX corregido y ruta de usuarios re-habilitada */}
        <Route path="/admin/usuarios" element={<AdminUsuarios />} />
        <Route path="/admin/vidrios" element={<AdminVidrios />} />
      </Route>
    </Routes>
  );
}

export default App;