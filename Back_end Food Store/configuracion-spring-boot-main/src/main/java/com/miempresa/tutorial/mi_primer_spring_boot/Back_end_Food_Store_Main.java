package com.miempresa.tutorial.mi_primer_spring_boot;


import com.miempresa.tutorial.mi_primer_spring_boot.dtos.categoria.CategoriaCreate;
import com.miempresa.tutorial.mi_primer_spring_boot.dtos.categoria.CategoriaDto;
import com.miempresa.tutorial.mi_primer_spring_boot.dtos.categoria.CategoriaEdit;
import com.miempresa.tutorial.mi_primer_spring_boot.dtos.detallePedido.DetallePedidoCreate;
import com.miempresa.tutorial.mi_primer_spring_boot.dtos.detallePedido.DetallePedidoDto;
import com.miempresa.tutorial.mi_primer_spring_boot.dtos.pedido.PedidoDto;
import com.miempresa.tutorial.mi_primer_spring_boot.dtos.producto.ProductoCreate;
import com.miempresa.tutorial.mi_primer_spring_boot.dtos.producto.ProductoDto;
import com.miempresa.tutorial.mi_primer_spring_boot.dtos.producto.ProductoEdit;
import com.miempresa.tutorial.mi_primer_spring_boot.dtos.usuario.UsuarioCreate;
import com.miempresa.tutorial.mi_primer_spring_boot.dtos.usuario.UsuarioDto;
import com.miempresa.tutorial.mi_primer_spring_boot.dtos.usuario.UsuarioEdit;
import com.miempresa.tutorial.mi_primer_spring_boot.enums.Estado;
import com.miempresa.tutorial.mi_primer_spring_boot.enums.FormaPago;
import com.miempresa.tutorial.mi_primer_spring_boot.enums.Rol;
import com.miempresa.tutorial.mi_primer_spring_boot.service.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.math.BigDecimal;
import java.util.*;


@SpringBootApplication
public class Back_end_Food_Store_Main {
    private static final Scanner scanner = new Scanner(System.in);


    private static ApplicationContext context;
    private static ProductoService productoService;
    private static CategoriaService categoriaService;
    private static DetallePedidoService detallePedidoService;
    private static PedidoService pedidoService;
    private static UsuarioService usuarioService;

    public static void main(String[] args) {
        context = SpringApplication.run(Back_end_Food_Store_Main.class, args);
        productoService = context.getBean(ProductoService.class);
        categoriaService = context.getBean(CategoriaService.class);
        detallePedidoService = context.getBean(DetallePedidoService.class);
        pedidoService = context.getBean(PedidoService.class);
        usuarioService = context.getBean(UsuarioService.class);
        boolean salir = false;
        while (!salir) {
            System.out.println();
            System.out.println("===== FOOD STORE - MENÚ PRINCIPAL =====");
            System.out.println("1. Gestionar Categorías");
            System.out.println("2. Gestionar Productos");
            System.out.println("3. Gestionar Usuarios");
            System.out.println("4. Gestionar Pedidos");
            System.out.println("5. Reportes");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            String op = scanner.nextLine().trim();
            switch (op) {
                case "1":
                    menuCategorias();
                    break;
                case "2":
                    menuProductos();
                    break;
                case "3":
                    menuUsuarios();
                    break;
                case "4":
                    menuPedidos();
                    break;
                case "5":
                    menuReportes();
                    break;
                case "0":
                    salir = true;
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }

        System.out.println("Aplicación finalizada.");
    }

    // ── Submenús ─────────────────────────────────────────────────

    private static void menuCategorias() {
        int opcionSubCat = -1;
        while (opcionSubCat != 0) {
            System.out.println("\n--- SUBMENU CATEGORÍAS ---");
            System.out.println("1. Alta");
            System.out.println("2. Baja Logica");
            System.out.println("3. Modificacion");
            System.out.println("4. Listado");
            System.out.println("0. Volver");
            System.out.print("Opción: ");
            try {
                opcionSubCat = Integer.parseInt(scanner.nextLine());
                switch (opcionSubCat) {
                    case 1:
                        altaCategoria();
                        break;
                    case 2:
                        bajaCategoria();
                        break;
                    case 3:
                        modificacionCategoria();
                        break;
                    case 4:
                        listadoCategorias();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opcion inválida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error de entrada.");
            }
        }
    }

    private static void altaCategoria() {
        System.out.println("\n Alta de Categoria");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim();
        if (nombre.isEmpty() || nombre.matches("^\\d.*")) {
            System.out.println("ERROR: El nombre no puede estar vacío.");
            return;
        }
        System.out.print("Descripción: ");
        String descripcion = scanner.nextLine().trim();

        CategoriaCreate cat = new CategoriaCreate(nombre, descripcion);
        CategoriaDto catCreada = categoriaService.save(cat);


        System.out.println("Categoría guardada con éxito. ID: " + catCreada.id());
    }

    private static List<CategoriaDto> listadoCategorias() {
        List<CategoriaDto> list = categoriaService.findAll();
        if (list.isEmpty()) {
            System.out.println("No existen categorias. Ingrese nuevas categorias: ");
            altaCategoria();
            list = categoriaService.findAll();
            return list;
        }
        System.out.println("\nCATEGORIAS");
        for (CategoriaDto c : list) {
            System.out.println("ID: " + c.id() + " | Nombre: " + c.nombre() + " | Descipcion: " + c.desciption());
        }
        return list;
    }

    private static void bajaCategoria() {
        System.out.println("\nBaja de Categoria");
        System.out.println("Categorias disponibles: ");
        listadoCategorias();
        System.out.print("Ingrese el ID: ");
        try {
            Long id = Long.parseLong(scanner.nextLine());
            CategoriaDto categoriaEncontrada = categoriaService.findById(id);
            categoriaService.deleteById(categoriaEncontrada.id());

        } catch (NumberFormatException e) {
            System.out.println("ID no numérico.");
        } catch (NullPointerException | IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void modificacionCategoria() {
        System.out.println("\nModificación de Categoria");
        if (!listadoCategorias().isEmpty()) {
            System.out.print("Ingrese ID a modificar: ");
            try {
                Long id = Long.parseLong(scanner.nextLine());
                CategoriaDto categoriaEncontrada = categoriaService.findById(id);
                System.out.println("Valores actuales: nombre: " + categoriaEncontrada.nombre() + " Descripcion " + categoriaEncontrada.desciption());

                System.out.print("Nuevo Nombre (presione Enter para conservar): ");
                String nNombre = scanner.nextLine().trim();
                System.out.print("Nueva Descripción (presione Enter para conservar): ");
                String nDesc = scanner.nextLine().trim();


                CategoriaEdit nuevaCat;
                if (!nNombre.isEmpty() && !nDesc.isEmpty()) {
                    nuevaCat = new CategoriaEdit(nNombre, nDesc);
                    categoriaService.update(nuevaCat, categoriaEncontrada.id());
                } else if (!nNombre.isEmpty()) {
                    nuevaCat = new CategoriaEdit(nNombre, categoriaEncontrada.desciption());
                    categoriaService.update(nuevaCat, categoriaEncontrada.id());
                } else {
                    nuevaCat = new CategoriaEdit(categoriaEncontrada.nombre(), nDesc);
                    categoriaService.update(nuevaCat, categoriaEncontrada.id());
                }
                System.out.println("Modificacion completada.");
            } catch (NumberFormatException e) {
                System.out.println("ID no válido.");
            } catch (NullPointerException | IllegalArgumentException ex) {
                System.out.println(ex.getMessage());
            }

        } else {
            System.out.println("No existen categorias a modificar");
        }

    }


    //PRODUCTOS
    private static void menuProductos() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\nMENU PRODUCTOS ");
            System.out.println("1. Alta");
            System.out.println("2. Baja Lógica");
            System.out.println("3. Modificación");
            System.out.println("4. Listado");
            System.out.println("0. Volver");
            System.out.print("Opción: ");
            try {
                opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 1:
                        altaProducto();
                        break;
                    case 2:
                        bajaProducto();
                        break;
                    case 3:
                        modificacionProducto();
                        break;
                    case 4:
                        listadoProductos();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opcion invalida");
                }
            } catch (NumberFormatException e) {
                System.out.println("Ingrese valor valido");
            }
        }
    }

    private static void altaProducto() {
        System.out.println("\n Alta de Producto");
        listadoCategorias();
        System.out.print("Ingrese ID de Categoria: ");
        try {
            Long catId = Long.parseLong(scanner.nextLine());
            categoriaService.findById(catId);
            boolean disponible = true;
            System.out.print("Nombre Producto: ");
            String nombre = scanner.nextLine().trim();
            System.out.print("Descripción: ");
            String desc = scanner.nextLine().trim();
            System.out.print("Precio (> 0): ");
            BigDecimal precio = new BigDecimal(scanner.nextLine().trim());
            System.out.print("Stock (>= 0): ");
            int stock = Integer.parseInt(scanner.nextLine());
            System.out.print("Disponible? S/N: ");
            String disponibleString = scanner.nextLine().trim();
            if (disponibleString.equalsIgnoreCase("n")) {
                disponible = false;
            } else if (!disponibleString.equalsIgnoreCase("s")) {
                System.out.println("Ingrese datos validos S o N " + disponibleString + " no es un dato valido");
                return;
            }


            if (precio.compareTo(BigDecimal.ZERO) <= 0 || stock < 0 || nombre.isEmpty()) {
                System.out.println("ERROR: Restricciones de validación no superadas.");
                return;
            }

            ProductoCreate prod = ProductoCreate.builder()
                    .nombre(nombre)
                    .disponible(disponible)
                    .idCategoria(catId)
                    .descripcion(desc)
                    .Imagen("Imagen.jpg")
                    .precio(precio)
                    .stock(stock)
                    .build();

            ProductoDto productoBD = productoService.save(prod);
            System.out.println("Producto creado con ID: " + productoBD.id() + " en Categoría: " + productoBD.categoriaDto().nombre());
        } catch (NumberFormatException e) {
            System.out.println("Error ingrese datos validos");
        } catch (NullPointerException ex) {
            System.out.println(ex.getMessage());
        }
    }


    private static List<ProductoDto> listadoProductos() {
        List<ProductoDto> list = productoService.findAll();
        System.out.println("\nPRODUCTOS ACTIVOS ");
        String categoriaNombre;
        Long categoriaId;
        if (!list.isEmpty()) {
            for (ProductoDto p : list) {
                if (p.categoriaDto() == null) {
                    categoriaNombre = "Sin categoria";
                    categoriaId = null;
                } else {
                    categoriaNombre = p.categoriaDto().nombre();
                    categoriaId = p.categoriaDto().id();
                }
                System.out.println("ID: " + p.id() + " | Nombre: " + p.nombre() + " | Precio: $" + p.precio() +
                        " | Stock: " + p.stock() + " | Categoria: " + categoriaId + " | Categoria nombre: " + categoriaNombre);
            }
        } else
            System.out.println("No se encontrar productos activos");
        return list;
    }

    private static void bajaProducto() {
        System.out.println("\nBaja Logica de Producto");
        listadoProductos();
        System.out.print("ID del Producto: ");
        try {
            Long id = Long.parseLong(scanner.nextLine());
            ProductoDto productoBaja = productoService.findById(id);
            productoService.deleteById(productoBaja.id());

        } catch (NumberFormatException e) {
            System.out.println("ID incorrecto.");
        } catch (NullPointerException | IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }


    private static void modificacionProducto() {
        System.out.println("\nModificacion de Producto");
        if (!listadoProductos().isEmpty()) {
            System.out.print("ID de Producto a modificar: ");
            try {
                Long id = Long.parseLong(scanner.nextLine());
                ProductoDto productoDto = productoService.findById(id);


                System.out.println("Actuales -> Nombre: " + productoDto.nombre() + " | Precio: $" + productoDto.precio() + " | Stock: " + productoDto.stock());


                String nuevoNombre = productoDto.nombre();
                BigDecimal nuevoPrecio = productoDto.precio();
                int nStk = productoDto.stock();


                boolean valido = false;
                while (!valido) {
                    try {
                        System.out.print("Nuevo nombre (presione Enter para conservar): ");
                        String nombreInput = scanner.nextLine().trim();
                        if (!nombreInput.isEmpty()) {
                            if (nombreInput.length() <= 1) {
                                System.out.println("Ingrese un nombre valido (más de 1 carácter)");
                                continue;
                            }
                            nuevoNombre = nombreInput;
                        }


                        System.out.print("Nuevo precio (presione Enter para conservar): ");
                        String precioInput = scanner.nextLine().trim();
                        if (!precioInput.isEmpty()) {
                            try {
                                BigDecimal parsedPrecio = new BigDecimal(precioInput);
                                if (parsedPrecio.compareTo(BigDecimal.ZERO) <= 0) {
                                    System.out.println("Ingrese un precio válido (mayor a 0)");
                                    continue;
                                }
                                nuevoPrecio = parsedPrecio;
                            } catch (NumberFormatException e) {
                                System.out.println("ERROR: El precio debe ser un número válido");
                                continue;
                            }
                        }

                        System.out.print("Nuevo stock (presione Enter para conservar): ");
                        String stockInput = scanner.nextLine().trim();
                        if (!stockInput.isEmpty()) {
                            try {
                                int parsedStock = Integer.parseInt(stockInput);
                                if (parsedStock < 0) {
                                    System.out.println("ERROR: Ingrese un stock válido (0 o mayor)");
                                    continue;
                                }
                                nStk = parsedStock;
                            } catch (NumberFormatException e) {
                                System.out.println("ERROR: El stock debe ser un número entero válido.");
                                continue;
                            }
                        }
                        Long categoriaId = (productoDto.categoriaDto() != null) ? productoDto.categoriaDto().id() : null;
                        ProductoEdit productoEdit = new ProductoEdit(
                                nuevoNombre,
                                nuevoPrecio,
                                productoDto.descripcion(),
                                nStk,
                                productoDto.Imagen(),
                                true,
                                categoriaId
                        );

                        productoService.update(productoEdit, id);

                        valido = true;
                        System.out.println("Producto actualizado!");

                    } catch (NumberFormatException e) {
                        System.out.println("ERROR Al actualizar: " + e.getMessage());
                    }


                }
            } catch (Exception e) {
                System.out.println("ERROR Al actualizar: " + e.getMessage());
            }
        } else {
            System.out.println("No hay pedidos a modificar");
        }
    }

    //MENU USUARIOS
    private static void menuUsuarios() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\nMENU USUARIOS ");
            System.out.println("1. Alta");
            System.out.println("2. Baja Lógica");
            System.out.println("3. Modificación");
            System.out.println("4. Listado");
            System.out.println("5. Buscar por mail");
            System.out.println("0. Volver");
            System.out.print("Opción: ");
            try {
                opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 1:
                        altaUsuario();
                        break;
                    case 2:
                        bajaUsuario();
                        break;
                    case 3:
                        modificacionUsuario();
                        break;
                    case 4:
                        listadoUsuarios();
                        break;
                    case 5:
                        buscarPorMail();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opcion invalida");
                }
            } catch (NumberFormatException e) {
                System.out.println("Ingrese valor valido");
            }
        }
    }

    private static void altaUsuario() {
        System.out.println("\n Alta de Usuario");
        System.out.print("Ingrese datos: ");
        try {
            System.out.print("Ingrese nombre: ");
            String nombre = scanner.nextLine().trim();
            System.out.print("Ingrese apellido: ");
            String apellido = scanner.nextLine().trim();
            System.out.print("Ingrese mail: ");
            String mail = scanner.nextLine().trim();
            System.out.print("Ingrese celular: ");
            String celular = scanner.nextLine().trim();
            System.out.print("Ingrese contrasena: ");
            String contrasena = scanner.nextLine().trim();
            System.out.println("Opciones de rol:");
            Rol rolSeleccionado = null;
            while (rolSeleccionado == null) {
                System.out.println("Opciones de rol:");
                int contador = 1;
                for (Rol rol : Rol.values()) {
                    System.out.println(contador + " - " + rol);
                    contador++;
                }
                System.out.print("Ingrese rol: ");
                try {
                    int entrada = Integer.parseInt(scanner.nextLine());
                    if (entrada >= 1 && entrada <= Rol.values().length) {
                        rolSeleccionado = Rol.values()[entrada - 1];
                    } else {
                        System.out.println("Error: El número debe estar entre 1 y " + Rol.values().length + ".");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error: Entrada no válida. Por favor, ingrese un número.");
                }


            }
            if (nombre.isEmpty() || apellido.isEmpty() || mail.isEmpty() || contrasena.isEmpty()) {
                System.out.println("ERROR: Restricciones de validación no superadas.");
                return;
            }
            UsuarioCreate user = UsuarioCreate.builder()
                    .nombre(nombre)
                    .apellido(apellido)
                    .mail(mail)
                    .celular(celular)
                    .contrasena(contrasena)
                    .rol(rolSeleccionado)
                    .build();

            UsuarioDto userBD = usuarioService.save(user);
            System.out.println("Usuario creado con ID: " + userBD.id());

        } catch (NumberFormatException e) {
            System.out.println("Error ingrese datos validos");
        } catch (NullPointerException | IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static List<UsuarioDto> listadoUsuarios() {
        List<UsuarioDto> list = usuarioService.findAll();
        if (list.isEmpty()) {
            System.out.println("No existen usuarios en la BD ");
            return null;
        }
        System.out.println("\nUSUARIOS");
        for (UsuarioDto user : list) {
            System.out.println("ID: " + user.id() + " | Nombre: " + user.nombre() + " " + user.apellido() + "| Mail: " + user.mail() + "| Rol: " + user.rol() + user.pedidos());
        }

        return list;
    }


    private static void bajaUsuario() {
        System.out.println("\nBaja Logica de Usuario");
        listadoUsuarios();
        System.out.print("ID del Usuario: ");
        try {
            Long id = Long.parseLong(scanner.nextLine());
            usuarioService.deleteById(id);
            System.out.println("Usuario con id " + id + " eliminado exitosamente");

        } catch (NumberFormatException e) {
            System.out.println("ID incorrecto.");
        } catch (NullPointerException | IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void modificacionUsuario() {
        System.out.println("\nModificacion de Usuario");
        if (listadoUsuarios() != null) {
            System.out.print("ID de Usuario a modificar: ");
            try {
                Long id = Long.parseLong(scanner.nextLine());
                UsuarioDto usuarioDto = usuarioService.findById(id);


                System.out.println("Actuales -> Nombre: " + usuarioDto.nombre() + " Apellido: " + usuarioDto.apellido() + " Celular: " + usuarioDto.celular() + " Mail: " + usuarioDto.mail());

                String nuevoNombre = usuarioDto.nombre();
                String nuevoApellido = usuarioDto.apellido();
                String nuevoCelular = usuarioDto.celular();
                String nuevoMail = usuarioDto.mail();

                boolean valido = false;
                while (!valido) {
                    try {
                        System.out.print("Nuevo nombre (presione Enter para conservar): ");
                        String nombreInput = scanner.nextLine().trim();
                        if (!nombreInput.isEmpty()) {
                            if (nombreInput.length() <= 1) {
                                System.out.println("Ingrese un nombre valido (más de 1 carácter)");
                                continue;
                            }
                            nuevoNombre = nombreInput;
                        }

                        System.out.print("Nuevo Apellido (presione Enter para conservar): ");
                        String apellidoInput = scanner.nextLine().trim();
                        if (!apellidoInput.isEmpty()) {
                            if (apellidoInput.length() <= 1) {
                                System.out.println("Ingrese un apellido valido (más de 1 carácter)");
                                continue;
                            }
                            nuevoApellido = apellidoInput;
                        }

                        System.out.print("Nuevo Celular (presione Enter para conservar): ");
                        String celularInput = scanner.nextLine().trim();
                        if (!celularInput.isEmpty()) {
                            if (celularInput.length() <= 1) {
                                System.out.println("Ingrese un apellido valido (más de 1 carácter)");
                                continue;
                            }
                            nuevoCelular = celularInput;
                        }

                        System.out.print("Nuevo Mail (presione Enter para conservar): ");
                        String mailInput = scanner.nextLine().trim();
                        if (!mailInput.isEmpty()) {
                            if (mailInput.length() <= 1) {
                                System.out.println("Ingrese un apellido valido (más de 1 carácter)");
                                continue;
                            }
                            nuevoMail = mailInput;
                        }

                        System.out.print("Nueva Contraseña (Ingrese nueva contrasena): ");
                        String contrasenaInput = scanner.nextLine().trim();
                        if (!contrasenaInput.isEmpty()) {
                            if (contrasenaInput.length() <= 1) {
                                System.out.println("Ingrese una contrasena valida (más de 1 carácter)");
                                continue;
                            }
                        } else {
                            System.out.println("Ingrese una contrasena");
                            continue;
                        }

                        UsuarioEdit usuarioEdit = new UsuarioEdit(
                                nuevoNombre,
                                nuevoApellido,
                                nuevoMail,
                                nuevoCelular,
                                contrasenaInput
                        );
                        usuarioService.update(usuarioEdit, id);
                        valido = true;
                        System.out.println("Usuario actualizado!");

                    } catch (NumberFormatException e) {
                        System.out.println("ERROR Al actualizar: " + e.getMessage());
                    }
                }
            } catch (Exception e) {
                System.out.println("ERROR Al actualizar: " + e.getMessage());
            }
        }
    }

    private static void buscarPorMail() {
        System.out.println("Ingrese mail de usuario para buscar: ");
        try {
            String mailInput = scanner.nextLine().trim();
            UsuarioDto usuarioDto = usuarioService.findByMail(mailInput);
            System.out.println("Usuario encontrado: ");
            System.out.println("Nombre: " + usuarioDto.nombre() + " Apellido: " + usuarioDto.apellido() + " Celular: " + usuarioDto.celular() + " Mail: " + usuarioDto.mail());

        } catch (NullPointerException ex) {
            System.out.println(ex.getMessage());
        }

    }

    private static void menuPedidos() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n===== MENU PEDIDOS =====");
            System.out.println("1. Alta");
            System.out.println("2. Baja Lógica");
            System.out.println("3. Modificar estado de pedido");
            System.out.println("4. Listado");
            System.out.println("5. Buscar pedidos por Usuario");
            System.out.println("6. Buscar pedidos por Estado");
            System.out.println("0. Volver");
            System.out.print("Opción: ");
            try {
                opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 1:
                        altaPedido();
                        break;
                    case 2:
                        bajaPedido();
                        break;
                    case 3:
                        cambiarEstado();
                        break;
                    case 4:
                        listadoPedidos();
                        break;
                    case 5:
                        buscarPedidoPorUsuario();
                        break;
                    case 6:
                        buscarPedidoPorEstado();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opción inválida");
                }
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un valor válido numérico");
            }
        }
    }


    private static void altaPedido() {
        System.out.println("\n Alta de Pedido");
        listadoUsuarios();
        System.out.print("Ingrese ID de Usuario: ");
        try {
            Long userId = Long.parseLong(scanner.nextLine());
            usuarioService.findById(userId);
            System.out.print("Eliga forma de Pago: ");
            FormaPago formaPago = null;
            while (formaPago == null) {
                System.out.println("Opciones de pago:");
                int contador = 1;
                for (FormaPago forma : FormaPago.values()) {
                    System.out.println(contador + " - " + forma);
                    contador++;
                }
                System.out.print("Ingrese forma de pago: ");
                try {
                    int entrada = Integer.parseInt(scanner.nextLine());
                    if (entrada >= 1 && entrada <= FormaPago.values().length) {
                        formaPago = FormaPago.values()[entrada - 1];
                    } else {
                        System.out.println("Error: El número debe estar entre 1 y " + FormaPago.values().length + ".");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error: Entrada no válida. Por favor, ingrese un número.");
                }
            }
            boolean valido = false;
            Long idPedido = null;
            while (!valido) {
                listadoProductos();
                System.out.println("Seleccione id de producto a agregar al pedido");
                try {
                    Long id = Long.parseLong(scanner.nextLine());
                    ProductoDto productoDto = productoService.findById(id);
                    System.out.println("Ingrese cantidad de compra: ");
                    int cantidad = Integer.parseInt(scanner.nextLine());
                    if (cantidad <= 0) {
                        System.out.println("La cantidad debe ser mayor a 0.");
                        continue;
                    }
                    if (productoDto.stock() <= cantidad) {
                        System.out.println("Stock insuficiente. Stock actual: " + productoDto.stock());
                        continue;
                    }
                    DetallePedidoCreate compraNueva = new DetallePedidoCreate(
                            cantidad, productoDto.precio().multiply(BigDecimal.valueOf(cantidad)), id, idPedido, true
                    );
                    DetallePedidoDto detalle = detallePedidoService.save(compraNueva, formaPago, userId);
                    idPedido = detalle.pedidoId();
                    System.out.println("Agregado con exito! Quisiera agregar otro item al carrito? ");
                    boolean respuestaValida = false;
                    while (!respuestaValida) {
                        System.out.print("Quisiera agregar otro ítem al carrito? (S/N): ");
                        String opcion = scanner.nextLine().trim();
                        if (opcion.equalsIgnoreCase("n")) {
                            valido = true;
                            respuestaValida = true;
                        } else if (opcion.equalsIgnoreCase("s")) {
                            respuestaValida = true;
                        } else {
                            System.out.println("Error: '" + opcion + "' no es una opción válida. Ingrese S para Sí o N para No.");
                        }
                    }
                } catch (NumberFormatException nE) {
                    System.out.println("Ingrese un numero valido");
                }

            }
            //Sale bucle valido
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void listadoPedidos() {
        List<PedidoDto> list = pedidoService.findAll();
        if (list.isEmpty()) {
            System.out.println("No existen pedidos en la BD ");
            return;
        }
        System.out.println("\nPEDIDOS");
        for (PedidoDto p : list) {
            System.out.println("ID: " + p.id() + " | Fecha: " + p.fecha() + " | Estado: " + p.estado() + " | Forma de Pago: " + p.formaPago() + " | Total: " + p.total());
        }

    }

    public static void bajaPedido() {
        System.out.println("\nBaja Logica de Pedidos");
        listadoPedidos();
        System.out.print("ID del Pedido: ");
        try {
            Long id = Long.parseLong(scanner.nextLine());
            pedidoService.deleteById(id);
            System.out.println("Pedido con id " + id + " eliminado exitosamente");

        } catch (NumberFormatException e) {
            System.out.println("ID incorrecto.");
        } catch (NullPointerException | IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public static void cambiarEstado() {
        System.out.println("\nCambio de estado de Pedidos");
        listadoPedidos();
        System.out.print("ID del Pedido: ");
        try {
            Long id = Long.parseLong(scanner.nextLine());
            PedidoDto pedido = pedidoService.findById(id);
            System.out.println("Estado actual: " + pedido.estado());
            Estado estadoNuevo = null;
            while (estadoNuevo == null) {
                System.out.println("Opciones de Estado:");
                int contador = 1;
                for (Estado estado : Estado.values()) {
                    System.out.println(contador + " - " + estado);
                    contador++;
                }
                System.out.print("Ingrese nuevo Estadoo: ");
                try {
                    int entrada = Integer.parseInt(scanner.nextLine());
                    if (entrada >= 1 && entrada <= FormaPago.values().length) {
                        estadoNuevo = Estado.values()[entrada - 1];
                    } else {
                        System.out.println("Error: El número debe estar entre 1 y " + Estado.values().length + ".");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error: Entrada no válida. Por favor, ingrese un número.");
                }
            }
            //Fin bucle while
            pedidoService.actualizarEstado(id, estadoNuevo);
            System.out.println("Estado pedido actualizado correctamente! ");

        } catch (NumberFormatException e) {
            System.out.println("ID incorrecto.");
        } catch (NullPointerException | IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void buscarPedidoPorUsuario() {
        System.out.println("Usuario disponibles: ");
        listadoUsuarios();
        System.out.println("Ingrese id del Usuario deseado: ");
        try {
            Long id = Long.parseLong(scanner.nextLine());
            UsuarioDto usuario = usuarioService.findById(id);
            if (usuario.pedidos().isEmpty()) {
                System.out.println("Usuario no tiene ningun pedido");
                return;
            }
            usuario.pedidos().stream()
                    .filter(PedidoDto::activo)
                    .forEach(pedido -> System.out.println("Pedido : ID: " + pedido.id() + " | Fecha: " + pedido.fecha() + " | Estado: " + pedido.estado() + " | Total: " + pedido.total()));

        } catch (NullPointerException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public static void buscarPedidoPorEstado() {
        System.out.println("Estados disponibles: ");
        try {
            Estado estadoNuevo = null;
            while (estadoNuevo == null) {
                System.out.println("Opciones de Estado:");
                int contador = 1;
                for (Estado estado : Estado.values()) {
                    System.out.println(contador + " - " + estado);
                    contador++;
                }
                System.out.print("Ingrese Estadoo: ");
                try {
                    int entrada = Integer.parseInt(scanner.nextLine());
                    if (entrada >= 1 && entrada <= FormaPago.values().length) {
                        estadoNuevo = Estado.values()[entrada - 1];
                    } else {
                        System.out.println("Error: El número debe estar entre 1 y " + Estado.values().length + ".");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error: Entrada no válida. Por favor, ingrese un número.");
                }
            }
            //Salida bucle while
            List<PedidoDto> pedidos=  pedidoService.findByEstadoAndActivo(estadoNuevo);
            pedidos.forEach(pedido -> System.out.println("Pedido : ID: " + pedido.id() + " | Fecha: " + pedido.fecha() + " | Estado: " + pedido.estado() + " | Total: " + pedido.total()));
        } catch (NullPointerException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void menuReportes() {
        int opcionSubCat = -1;
        while (opcionSubCat != 0) {
            System.out.println("\n--- SUBMENU REPORTES ---");
            System.out.println("1. Productos por Categoria");
            System.out.println("2. Pedidos por usuario");
            System.out.println("3. Pedidos por Estado");
            System.out.println("4. Total Facturado");
            System.out.println("0. Volver");
            System.out.print("Opción: ");
            try {
                opcionSubCat = Integer.parseInt(scanner.nextLine());
                switch (opcionSubCat) {
                    case 1:
                        productosPorCategoria();
                        break;
                    case 2:
                        pedidosPoorUsuario();
                        break;
                    case 3:
                        pedidosPoorEstado();
                        break;
                    case 4:
                        totalFacturado();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opcion inválida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error de entrada.");
            }
        }
    }

    public static void productosPorCategoria(){
        System.out.println("Ingrese ID de la categoria por la cual quiere buscar productos: ");
        listadoCategorias();
        try {
            Long catId = Long.parseLong(scanner.nextLine());
            List<ProductoDto> productosPorCat= productoService.findByCategoriaIdAndDisponible(catId,true);
            productosPorCat.forEach(p-> System.out.println("ID: " + p.id() + " | Nombre: " + p.nombre() + " | Precio: $" + p.precio() + " | Stock: " + p.stock()));
        } catch (NullPointerException | IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }
    public static void pedidosPoorUsuario(){
        buscarPedidoPorUsuario();
    }
    public static void pedidosPoorEstado(){
        buscarPedidoPorEstado();
    }
    public static void totalFacturado(){
        try {
            List<PedidoDto> pedidos=  pedidoService.findByEstadoAndActivo(Estado.TERMINADO);
            BigDecimal totalFacturado = pedidos.stream()
                    .map(PedidoDto::total)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            System.out.println("Total facturado: $" + totalFacturado);
        }
        catch (NullPointerException ex){
            System.out.println(ex.getMessage());
        }

    }

}


