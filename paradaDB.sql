

select * from productos

USE parada_db;

INSERT INTO productos (id, nombre, categoria, descripcion, precio, emoji) VALUES
(1,  'Café americano grande',   'Café',             'Café recién hecho, 16 oz.',          12.00, 'Cafe.png'),
(2,  'Capuchino',               'Café',             'Espresso con leche espumada.',       18.00, 'Capuccino.jpg'),
(3,  'Croissant de mantequilla','Panadería',        'Horneado en la mañana.',             10.00, 'crossait.jpg'),
(4,  'Dona glaseada',           'Panadería',        'Clásica, con glaseado de vainilla.',  7.00, 'dona.jpg'),
(5,  'Hot dog completo',        'Comida preparada', 'Salchicha, pan suave y aderezos.',   22.00, 'hotdog.jpg'),
(6,  'Sándwich de pollo',       'Comida preparada', 'Pollo, lechuga y tomate.',           28.00, 'sandwich.jpg'),
(7,  'Pizza personal',          'Comida preparada', 'Queso y pepperoni.',                 32.00, 'pizza.jpg'),
(8,  'Agua pura 600 ml',        'Bebidas',          'Botella individual.',                 6.00, 'agua.jpg'),
(9,  'Gaseosa de cola 500 ml',  'Bebidas',          'Bien fría.',                          9.00, 'Gaseosa.jpg'),
(10, 'Jugo de naranja',         'Bebidas',          '100% jugo, 355 ml.',                 12.00, 'Jugo.jpg'),
(11, 'Papas fritas',            'Snacks',           'Bolsa 45 g, sabor original.',         8.00, 'papas.jpg'),
(12, 'Barra de granola',        'Snacks',           'Avena y miel.',                       7.00, 'Granola.jpg'),
(13, 'Chocolate con maní',      'Snacks',           'Barra 50 g.',                        10.00, 'Chocolate.jpg'),
(14, 'Galletas de avena',       'Despensa',         'Paquete familiar.',                  14.00, 'Galleta.jpg'),
(15, 'Pan de caja',             'Despensa',         'Rebanado, 600 g.',                   20.00, 'Pan.jpg'),
(16, 'Leche entera 1 L',        'Despensa',         'Pasteurizada.',                      12.00, 'Leche.jpg');
