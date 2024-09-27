START TRANSACTION;

SET SQL_SAFE_UPDATES = 0;
SET AUTOCOMMIT = 0;

-- Create category table
CREATE TABLE `bg_games`.`category` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(45) NULL,
  PRIMARY KEY (`id`));
  
-- Create mechanic table
CREATE TABLE `bg_games`.`mechanic` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(45) NULL,
  PRIMARY KEY (`id`));

-- Insert tags
INSERT INTO `bg_games`.`mechanic` (description) VALUES ("Acting"),("Action / Movement Programming"),("Action Point Allowance System"),("Area Control / Area Influence"),("Area Enclosure"),("Area Movement"),("Area-Impulse"),("Auction/Bidding"),("Betting/Wagering"),("Campaign / Battle Card Driven"),("Card Drafting"),("Chit-Pull System"),("Commodity Speculation"),("Cooperative Play"),("Crayon Rail System"),("Deck / Pool Building"),("Dice Rolling"),("Grid Movement"),("Hand Management"),("Hex-and-Counter"),("Hidden Traitor"),("Line Drawing"),("Memory"),("Modular Board"),("Paper-and-Pencil"),("Partnerships"),("Pattern Building"),("Pattern Recognition"),("Pick-up and Deliver"),("Player Elimination"),("Point to Point Movement"),("Press Your Luck"),("Rock-Paper-Scissors"),("Role Playing"),("Roll / Spin and Move"),("Rondel"),("Route/Network Building"),("Secret Unit Deployment"),("Set Collection"),("Simulation"),("Simultaneous Action Selection"),("Singing"),("Stock Holding"),("Storytelling"),("Take That"),("Tile Placement"),("Time Track"),("Trading"),("Trick-taking"),("Variable Phase Order"),("Variable Player Powers"),("Voting"),("Worker Placement");  
INSERT INTO 'bg_games'.'category' (description) VALUES ("Abstract Strategy"),("Action / Dexterity"),("Adventure"),("Age of Reason"),("American Civil War"),("American Indian Wars"),("American Revolutionary War"),("American West"),("Ancient"),("Animals"),("Arabian"),("Aviation / Flight"),("Bluffing"),("Book"),("Card Game"),("Childrens Game"),("City Building"),("Civil War"),("Civilization"),("Collectible Components"),("Comic Book / Strip"),("Deduction"),("Dice"),("Economic"),("Educational"),("Electronic"),("Environmental"),("Expansion for Base-game"),("Exploration"),("Fan Expansion"),("Fantasy"),("Farming"),("Fighting"),("Game System"),("Horror"),("Humor"),("Industry / Manufacturing"),("Korean War"),("Mafia"),("Math"),("Mature / Adult"),("Maze"),("Medical"),("Medieval"),("Memory"),("Miniatures"),("Modern Warfare"),("Movies / TV / Radio theme"),("Murder/Mystery"),("Music"),("Mythology"),("Napoleonic"),("Nautical"),("Negotiation"),("Novel-based"),("Number"),("Party Game"),("Pike and Shot"),("Pirates"),("Political"),("Post-Napoleonic"),("Prehistoric"),("Print & Play"),("Puzzle"),("Racing"),("Real-time"),("Religious"),("Renaissance"),("Science Fiction"),("Space Exploration"),("Spies/Secret Agents"),("Sports"),("Territory Building"),("Trains"),("Transportation"),("Travel"),("Trivia"),("Video Game Theme"),("Vietnam War"),("Wargame"),("Word Game"),("World War I"),("World War II"),("Zombies");

-- Create game_categories table
CREATE TABLE `bg_games`.`game_categories` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `game_id` INT(11) NOT NULL,
  `category_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_game_id_idx` (`game_id` ASC) VISIBLE,
  INDEX `fk_category_id_idx` (`category_id` ASC) VISIBLE,
  CONSTRAINT `fk_game_id`
    FOREIGN KEY (`game_id`)
    REFERENCES `bg_games`.`game` (`game_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_category_id`
    FOREIGN KEY (`category_id`)
    REFERENCES `bg_games`.`category` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION); 
    
-- Create game_mechanics table
CREATE TABLE `bg_games`.`game_mechanics` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `game_id` INT(11) NOT NULL,
  `mechanic_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `game_id_idx` (`game_id` ASC) INVISIBLE,
  INDEX `mechanic_id_idx` (`mechanic_id` ASC) VISIBLE,
  CONSTRAINT `game_id`
    FOREIGN KEY (`game_id`)
    REFERENCES `bg_games`.`game` (`game_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `mechanic_id`
    FOREIGN KEY (`id`)
    REFERENCES `bg_games`.`mechanic` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
COMMIT;