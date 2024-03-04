# Test Task - Ether Tree App

Do: Бирюков Aлександр

Contact: [Telegram](https://t.me/SanyaLn), [HH.ru](https://kazan.hh.ru/resume/c458e78eff0c0dd3760039ed1f7047504d6247)

## Test task:

1. Создать структуру дерева, состоящую из узлов (Node) имеющих, название, детей и ссылку на
   родителя (такие же узлы)
2. Написать приложение состоящее из одного экрана с рекурсивной навигацией внутри этого дерева. (
   Первый экран открывает корневой уровень (root) и далее мы можем пройти в экраны childs)
3. Добавить возможность создания и удаления сущностей на каждом уровне
4. Сохранять состояние дерева на устройстве и подтягивать при следующем входе
5. Название формировать из последних 20 байт хэша узла по аналогии с адресом кошельков Ethereum

## Result

![Result Main Screen Photo](/README/main_screen.png)

### Design

![Figma Design All Version Photo](/README/design.png)
[Figma Link](https://www.figma.com/file/hnhO6euzyY4L1H4HAZNokc/Ether-Tree-App?type=design&node-id=0%3A1&mode=design&t=76dxdPKoT3ZRM5DZ-1)

Node:
- id -> increment sequence
- address -> HashCode.last(20) `Генерация адреса ноды происходит после вставки в db, тк hash генерируется из id+parentId, а id генерируется только после вставки в базу данных`
- parent -> id-parent
