/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.carlosgrau.control;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.carlosgrau.beans.Celda;
import net.carlosgrau.beans.Fila;

/**
 *
 * @author a021792876p
 */
public class Control extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        // StringBuilder formulario = null;
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
        Gson tabla = new Gson();
        String salto = request.getParameter("alto").trim();
        String sancho = request.getParameter("ancho").trim();
        String exp = "^[1-9][0-9]*";
        try {
            /* TODO output your page here. You may use following sample code. */

            if (salto.matches(exp) && sancho.matches(exp)) {
                Integer alto = Integer.parseInt(request.getParameter("alto"));
                Integer ancho = Integer.parseInt(request.getParameter("ancho"));
                if (alto <= 100 && ancho <= 100 && ancho > 0 && alto > 0) {

                    Celda celda;
                    Fila filas = new Fila();
                    ArrayList<Fila> contenido = new ArrayList();
                    ArrayList<Celda> celdas;
                    for (int i = 1; i <= alto; i++) {
                        filas.setI(i);

                        celdas = new ArrayList();
                        for (int j = 1; j <= ancho; j++) {
                            celda = new Celda();
                            celda.setOper1(i);
                            celda.setOper2(j);
                            celda.setRes(i * j);
                            celdas.add(celda);
                        }
                        filas.setArray(celdas);
                        contenido.add(filas);
                        filas = new Fila();
                    }

                    String strJson = tabla.toJson(contenido);
                    out.print(strJson);

                } else {
                    throw new NumberFormatException();

                }
            } else {
                throw new IllegalArgumentException();
            }
        } catch (NumberFormatException nfe) {
            response.setStatus(500);
            String error = "Debe introducir numeros entre 1 y 100 (ambos incluidos).";
            String strJson = tabla.toJson(error);
            out.print(strJson);
        } catch (IllegalArgumentException iae) {
            response.setStatus(500);
            String error;
            if (salto.equals("") || sancho.equals("")) {
                error = "No puede dejar campos vacíos.";
            } else {
                error = "Debe introducir sólo numeros enteros.";
            }
            String strJson = tabla.toJson(error);
            out.print(strJson);
        }
    }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
