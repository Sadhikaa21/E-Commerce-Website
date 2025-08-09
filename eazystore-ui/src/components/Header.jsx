import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faShoppingBasket,
  faTags,
  faAngleDown,
} from "@fortawesome/free-solid-svg-icons";
import { useState, useEffect, useRef } from "react";
import { Link, NavLink, useLocation, useNavigate } from "react-router-dom";
import { useCart } from "../store/cart-context";
import { useAuth } from "../store/auth-context";
import { toast } from "react-toastify";

export default function Header() {
  const isAdmin = true;
  const [isUserMenuOpen, setUserMenuOpen] = useState(false);
  const [isAdminMenuOpen, setAdminMenuOpen] = useState(false);
  const location = useLocation();
  const userMenuRef = useRef();
  const navigate = useNavigate();

  const toggleAdminMenu = () => setAdminMenuOpen((prev) => !prev);
  const toggleUserMenu = () => setUserMenuOpen((prev) => !prev);

  const { totalQuantity } = useCart();
  const { isAuthenticated, user, logout } = useAuth();

  useEffect(() => {
    setAdminMenuOpen(false);
    setUserMenuOpen(false);
    const handleClickOutside = (event) => {
      if (userMenuRef.current && !userMenuRef.current.contains(event.target)) {
        setUserMenuOpen(false);
        setAdminMenuOpen(false);
      }
    };
    document.addEventListener("mousedown", handleClickOutside);
    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, [location.pathname]);

  const handleLogout = (e) => {
    e.preventDefault();
    logout();
    toast.success("Logged out successfully!");
    navigate("/home");
  };

  const navLinkClass =
    "text-center text-lg font-primary font-semibold text-primary py-2 hover:text-dark";

  const dropdownLinkClass =
    "block w-full text-left px-4 py-2 text-lg font-primary font-semibold text-primary hover:bg-gray-100";

  return (
    <header className="border-b border-gray-300 sticky top-0 z-20 bg-#e5e5e5">
      <div className="flex items-center justify-between mx-auto max-w-[1152px] px-6 py-4">
        <Link to="/" className={navLinkClass}>
          <FontAwesomeIcon icon={faTags} className="h-8 w-8" />
          <span className="font-bold">Eazy Stickers</span>
        </Link>
        <nav className="flex items-center py-2 z-10">
          <ul className="flex space-x-6">
            <li>
              <NavLink
                to="/home"
                className={({ isActive }) =>
                  isActive ? `underline ${navLinkClass}` : navLinkClass
                }
              >
                Home
              </NavLink>
            </li>
            <li>
              <NavLink
                to="/about"
                className={({ isActive }) =>
                  isActive ? `underline ${navLinkClass}` : navLinkClass
                }
              >
                About
              </NavLink>
            </li>
            <li>
              <NavLink
                to="/contact"
                className={({ isActive }) =>
                  isActive ? `underline ${navLinkClass}` : navLinkClass
                }
              >
                Contact
              </NavLink>
            </li>
            <li>
              {isAuthenticated ? (
                <div className="relative" ref={userMenuRef}>
                  <button
                    onClick={toggleUserMenu}
                    className="relative text-primary"
                  >
                    <span className={navLinkClass}>
                      {`Hello ${
                        user.name.length > 5
                          ? `${user.name.slice(0, 5)}...`
                          : user.name
                      }`}
                    </span>
                    <FontAwesomeIcon
                      icon={faAngleDown}
                      className="text-primary w-6 h-6"
                    />
                  </button>
                  {isUserMenuOpen && (
                    <div className="absolute right-0 w-48 bg-white border border-gray-300 rounded-md shadow-lg z-20 transition ease-in-out duration-200">
                      <ul className="py-2">
                        <li>
                          <Link to="/profile" className={dropdownLinkClass}>
                            Profile
                          </Link>
                        </li>
                        <li>
                          <Link to="/orders" className={dropdownLinkClass}>
                            Orders
                          </Link>
                        </li>
                        {isAdmin && (
                          <li>
                            <button
                              onClick={toggleAdminMenu}
                              className={`${dropdownLinkClass} flex items-center justify-between`}
                            >
                              Admin
                              <FontAwesomeIcon icon={faAngleDown} />
                            </button>
                            {isAdminMenuOpen && (
                              <ul className="ml-4 mt-2 space-y-2">
                                <li>
                                  <Link
                                    to="/admin/orders"
                                    className={dropdownLinkClass}
                                  >
                                    Orders
                                  </Link>
                                </li>
                                <li>
                                  <Link
                                    to="/admin/messages"
                                    className={dropdownLinkClass}
                                  >
                                    Messages
                                  </Link>
                                </li>
                              </ul>
                            )}
                          </li>
                        )}
                        <li>
                          <Link
                            to="/home"
                            onClick={handleLogout}
                            className={dropdownLinkClass}
                          >
                            Logout
                          </Link>
                        </li>
                      </ul>
                    </div>
                  )}
                </div>
              ) : (
                <NavLink
                  to="/login"
                  className={({ isActive }) =>
                    isActive ? `underline ${navLinkClass}` : navLinkClass
                  }
                >
                  Login
                </NavLink>
              )}
            </li>
            <li>
              <Link to="/cart" className="relative text-primary py-2">
                <FontAwesomeIcon
                  icon={faShoppingBasket}
                  className="text-primary w-6"
                />
                <div className="absolute -top-2 -right-6 text-xs bg-yellow-400 text-black font-semibold rounded-full px-2 py-1 leading-none">
                  {totalQuantity}
                </div>
              </Link>
            </li>
          </ul>
        </nav>
      </div>
    </header>
  );
}
