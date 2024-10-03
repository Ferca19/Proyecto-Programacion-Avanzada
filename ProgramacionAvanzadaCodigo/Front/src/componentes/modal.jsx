import React from 'react';

const Modal = ({ isVisible, onClose, children }) => {
    console.log("Modal isVisible:", isVisible); // Agrega este log para verificar
    if (!isVisible) return null;

    return (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
            <div className="bg-white p-6 rounded-lg shadow-lg relative max-w-lg mx-auto">

                <button onClick={onClose} className="absolute top-2 right-2 text-gray-500">&times;</button>
                {children}
            </div>
        </div>
    );
};

export default Modal;
