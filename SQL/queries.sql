select RegistrarUsuario('nhrot'::varchar, '123'::varchar, 'nhrotexample.com'::varchar, 'activo'::varchar::varchar);
select RegistrarUsuario('jdoe'::varchar, '123'::varchar, 'jdoeexample.com'::varchar, 'activo'::varchar);
select RegistrarUsuario('asmith'::varchar, '123'::varchar, 'asmithexample.com'::varchar, 'activo'::varchar);
select RegistrarUsuario('mjohnson'::varchar, '123'::varchar, 'mjohnsonexample.com'::varchar, 'activo'::varchar);
select RegistrarUsuario('kclark'::varchar, '123'::varchar, 'kclarkexample.com'::varchar, 'activo'::varchar);
select RegistrarUsuario('tadams'::varchar, '123'::varchar, 'tadamsexample.com'::varchar, 'activo'::varchar);
select RegistrarUsuario('bwhite'::varchar, 'white5678'::varchar, 'bwhiteexample.com'::varchar, 'activo'::varchar);
select RegistrarUsuario('nlane'::varchar, 'lanepass1'::varchar, 'nlaneexample.com'::varchar, 'activo'::varchar);
select RegistrarUsuario('ahall'::varchar, 'hallpass23'::varchar, 'ahallexample.com'::varchar, 'activo'::varchar);
select RegistrarUsuario('mjames'::varchar, 'james123'::varchar, 'mjamesexample.com'::varchar, 'activo'::varchar);
select RegistrarUsuario('lscott'::varchar, 'scott!234'::varchar, 'lscottexample.com'::varchar, 'activo'::varchar);
select RegistrarUsuario('slopez'::varchar, 'lopez7890'::varchar, 'slopezexample.com'::varchar, 'activo'::varchar);
select RegistrarUsuario('wking'::varchar, 'king!pass'::varchar, 'wkingexample.com'::varchar, 'activo'::varchar);
select RegistrarUsuario('rhill'::varchar, 'hill1234'::varchar, 'rhillexample.com'::varchar, 'activo'::varchar);
select RegistrarUsuario('dgonzalez'::varchar, 'gonzalez1'::varchar, 'dgonzalezexample.com'::varchar, 'activo'::varchar);
select RegistrarUsuario('tgreen'::varchar, 'greenpass!'::varchar, 'tgreenexample.com'::varchar, 'activo'::varchar);
select RegistrarUsuario('cmartin'::varchar, 'martin456'::varchar, 'cmartinexample.com'::varchar, 'activo'::varchar);
select RegistrarUsuario('hlee'::varchar, 'lee2022$'::varchar, 'hleeexample.com'::varchar, 'activo'::varchar);
select RegistrarUsuario('pwalker'::varchar, 'walker#890'::varchar, 'pwalkerexample.com'::varchar, 'activo'::varchar);
select RegistrarUsuario('yyoung'::varchar, 'young!pass'::varchar, 'yyoungexample.com'::varchar, 'activo'::varchar);
select RegistrarUsuario('blong'::varchar, 'long1234'::varchar, 'blongexample.com'::varchar, 'activo'::varchar);


select crearchat(
'Default Chat'::varchar, 
'Chat de Prueba'::varchar, 
'individual'::varchar, 
'https://raw.githubusercontent.com/vlang/vsl/main/static/vsl-logo.png?sanitize=true'::varchar,
NOW()
);
	
select crearchat(
'Grupal Chat'::varchar, 
'Chat que debe ser grupal'::varchar, 
'grupal'::varchar, 
'https://static01.nyt.com/images/2024/01/05/multimedia/ND-Poke-bowl-bfwm/ND-Poke-bowl-bfwm-superJumbo.jpg'::varchar, 
NOW()
);


select AgregarusuarioChat(1, 1);
select AgregarusuarioChat(1, 2);

select AgregarusuarioChat(2, 2);
select AgregarusuarioChat(2, 3);
select AgregarusuarioChat(2, 4);
select AgregarusuarioChat(2, 5);

select * from mensaje m;







