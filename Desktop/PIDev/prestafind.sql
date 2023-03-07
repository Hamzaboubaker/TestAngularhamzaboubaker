-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 15, 2023 at 08:33 AM
-- Server version: 10.4.27-MariaDB
-- PHP Version: 8.1.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `prestafind`
--

-- --------------------------------------------------------

--
-- Table structure for table `adresses`
--

CREATE TABLE `adresses` (
  `id` int(10) UNSIGNED NOT NULL,
  `numBatiment` int(10) UNSIGNED NOT NULL,
  `rue` varchar(80) NOT NULL,
  `ville` varchar(80) NOT NULL,
  `gouvernorat` varchar(80) NOT NULL,
  `codePostal` int(10) UNSIGNED NOT NULL,
  `pays` varchar(80) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `adresses`
--

INSERT INTO `adresses` (`id`, `numBatiment`, `rue`, `ville`, `gouvernorat`, `codePostal`, `pays`) VALUES
(5, 120, 'HabibBourguiba', 'Tunis', 'GrandTunis', 1000, 'Tunisie'),
(6, 120, 'HabibBourguiba', 'Tunis', 'GrandTunis', 1000, 'Tunisie'),
(10, 120, 'HabibBourguiba', 'Tunis', 'GrandTunis', 1000, 'Tunisie'),
(94, 120, 'HabibBourguiba', 'Tunis', 'GrandTunis', 1000, 'Tunisie'),
(298, 120, 'HabibBourguiba', 'Tunis', 'GrandTunis', 1000, 'Tunisie'),
(382, 120, 'HabibBourguiba', 'Tunis', 'GrandTunis', 1000, 'Tunisie'),
(451, 120, 'HabibBourguiba', 'Tunis', 'GrandTunis', 1000, 'Tunisie'),
(638, 120, 'HabibBourguiba', 'Tunis', 'GrandTunis', 1000, 'Tunisie'),
(648, 120, 'HabibBourguiba', 'Tunis', 'GrandTunis', 1000, 'Tunisie'),
(659, 120, 'HabibBourguiba', 'Tunis', 'GrandTunis', 1000, 'Tunisie'),
(666, 120, 'HabibBourguiba', 'Tunis', 'GrandTunis', 1000, 'Tunisie'),
(713, 120, 'HabibBourguiba', 'Tunis', 'GrandTunis', 1000, 'Tunisie'),
(723, 120, 'HabibBourguiba', 'Tunis', 'GrandTunis', 1000, 'Tunisie'),
(741, 120, 'HabibBourguiba', 'Tunis', 'GrandTunis', 1000, 'Tunisie'),
(893, 120, 'HabibBourguiba', 'Tunis', 'GrandTunis', 1000, 'Tunisie'),
(911, 120, 'HabibBourguiba', 'Tunis', 'GrandTunis', 1000, 'Tunisie'),
(945, 120, 'HabibBourguiba', 'Tunis', 'GrandTunis', 1000, 'Tunisie');

-- --------------------------------------------------------

--
-- Table structure for table `articles`
--

CREATE TABLE `articles` (
  `id` int(50) UNSIGNED NOT NULL,
  `auteur` varchar(50) DEFAULT NULL,
  `titre` varchar(200) NOT NULL,
  `contenu` mediumtext DEFAULT NULL,
  `datePublication` datetime NOT NULL DEFAULT current_timestamp(),
  `dateModification` datetime DEFAULT NULL,
  `nbrVues` int(11) UNSIGNED DEFAULT NULL,
  `nbrPartages` int(11) UNSIGNED DEFAULT NULL,
  `nbrLikes` int(11) UNSIGNED DEFAULT NULL,
  `nbrDislikes` int(11) UNSIGNED DEFAULT NULL,
  `nbrRapports` int(11) UNSIGNED DEFAULT NULL,
  `retireParModerateur` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `articles`
--

INSERT INTO `articles` (`id`, `auteur`, `titre`, `contenu`, `datePublication`, `dateModification`, `nbrVues`, `nbrPartages`, `nbrLikes`, `nbrDislikes`, `nbrRapports`, `retireParModerateur`) VALUES
(28, 'jdoe@example.com', 'titre2', 'contenu2', '2023-02-15 07:52:28', '2023-02-15 07:52:28', 1, 2, 3, 1, 2, 1),
(29, 'jdoe@example.com', 'titre2', 'contenu2', '2023-02-15 07:54:37', '2023-02-15 07:54:37', 1, 2, 3, 1, 2, 1);

-- --------------------------------------------------------

--
-- Table structure for table `categories`
--

CREATE TABLE `categories` (
  `nom` varchar(80) NOT NULL,
  `nbrGigs` int(11) DEFAULT NULL,
  `description` varchar(280) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `commandes`
--

CREATE TABLE `commandes` (
  `id` int(10) UNSIGNED NOT NULL,
  `date` date NOT NULL,
  `nom` varchar(80) DEFAULT NULL,
  `etat` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `commentaires`
--

CREATE TABLE `commentaires` (
  `id` int(20) UNSIGNED NOT NULL,
  `auteur` varchar(20) NOT NULL,
  `contenu` varchar(280) NOT NULL,
  `datePublication` date NOT NULL,
  `nbrLikes` int(11) UNSIGNED DEFAULT NULL,
  `nbrDislikes` int(11) UNSIGNED DEFAULT NULL,
  `nbrSignals` int(11) UNSIGNED DEFAULT NULL,
  `retireParModerateur` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `demandesajout`
--

CREATE TABLE `demandesajout` (
  `id_dm` int(10) UNSIGNED NOT NULL,
  `dateDemande` date DEFAULT NULL,
  `acceptee` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `factures`
--

CREATE TABLE `factures` (
  `id` int(10) UNSIGNED NOT NULL,
  `total` float UNSIGNED NOT NULL,
  `quantite` int(10) UNSIGNED NOT NULL,
  `prixUnitaire` float UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `gigs`
--

CREATE TABLE `gigs` (
  `id` int(10) UNSIGNED NOT NULL,
  `titre` varchar(100) NOT NULL,
  `descriptin` varchar(280) DEFAULT NULL,
  `prix` float DEFAULT NULL,
  `categorie` int(11) NOT NULL,
  `disponibilite` int(11) NOT NULL,
  `nbrSolicitations` int(10) UNSIGNED DEFAULT NULL,
  `URLImage` varchar(300) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `images`
--

CREATE TABLE `images` (
  `URL` varchar(280) NOT NULL,
  `titre` varchar(50) DEFAULT NULL,
  `dateUpload` date DEFAULT NULL,
  `taille` float UNSIGNED DEFAULT NULL,
  `largeur` int(11) UNSIGNED DEFAULT NULL,
  `hauteur` int(11) UNSIGNED DEFAULT NULL,
  `format` varchar(7) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `messages`
--

CREATE TABLE `messages` (
  `id` int(10) UNSIGNED NOT NULL,
  `date` date DEFAULT NULL,
  `etat` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tags`
--

CREATE TABLE `tags` (
  `nom` varchar(20) NOT NULL,
  `nbrArticles` int(10) UNSIGNED NOT NULL,
  `description` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `utilisateurs`
--

CREATE TABLE `utilisateurs` (
  `email` varchar(50) NOT NULL,
  `nom` varchar(30) DEFAULT NULL,
  `prenom` varchar(30) DEFAULT NULL,
  `pseudo` varchar(30) NOT NULL,
  `mdp` varchar(50) NOT NULL,
  `adresseId` int(10) UNSIGNED NOT NULL,
  `role` varchar(20) NOT NULL,
  `dateNaissance` date DEFAULT NULL,
  `sexe` varchar(10) NOT NULL,
  `numTel` bigint(15) DEFAULT NULL,
  `etat` varchar(30) NOT NULL,
  `URLPhotoProfil` varchar(250) DEFAULT NULL,
  `salt` varchar(20) DEFAULT NULL,
  `token` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `utilisateurs`
--

INSERT INTO `utilisateurs` (`email`, `nom`, `prenom`, `pseudo`, `mdp`, `adresseId`, `role`, `dateNaissance`, `sexe`, `numTel`, `etat`, `URLPhotoProfil`, `salt`, `token`) VALUES
('jdoe@example.com', 'Johs', 'Doe', 'johndoe451', '3cwhZGBRk8XGIozXpcLuhQIjXYoJQHM/1+n/s6TkqLc=', 451, 'USER', '2023-02-14', 'HOMME', 123456789, 'ACTIF', 'https://example.com/profile.jpg', NULL, NULL),
('jdoe@example.com382', 'Johs', 'Doe', 'johndoe382', 'PUq5LbyJP8YM6widdECSwLgm0v5FjtupOgK8d/DLZeU=', 382, 'USER', '2023-02-15', 'HOMME', 123456789, 'INACTIF', 'https://example.com/profile.jpg', 'v���/��', NULL),
('jdoe@example.com666', 'Johs', 'Doe', 'johndoe666', 'pEyWCTH9BcjukDdXL7KhFJb4HoIPmNHVVGkv1oLn/7E=', 666, 'USER', '2023-02-15', 'HOMME', 123456789, 'INACTIF', 'https://example.com/profile.jpg', '\\�W�\'$D', NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `adresses`
--
ALTER TABLE `adresses`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `articles`
--
ALTER TABLE `articles`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_articles` (`auteur`);

--
-- Indexes for table `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`nom`);

--
-- Indexes for table `commandes`
--
ALTER TABLE `commandes`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `commentaires`
--
ALTER TABLE `commentaires`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `demandesajout`
--
ALTER TABLE `demandesajout`
  ADD PRIMARY KEY (`id_dm`);

--
-- Indexes for table `factures`
--
ALTER TABLE `factures`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `gigs`
--
ALTER TABLE `gigs`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `images`
--
ALTER TABLE `images`
  ADD PRIMARY KEY (`URL`);

--
-- Indexes for table `messages`
--
ALTER TABLE `messages`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tags`
--
ALTER TABLE `tags`
  ADD PRIMARY KEY (`nom`);

--
-- Indexes for table `utilisateurs`
--
ALTER TABLE `utilisateurs`
  ADD PRIMARY KEY (`email`),
  ADD UNIQUE KEY `pseudo` (`pseudo`),
  ADD KEY `FK_utilisateurs` (`adresseId`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `adresses`
--
ALTER TABLE `adresses`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=946;

--
-- AUTO_INCREMENT for table `articles`
--
ALTER TABLE `articles`
  MODIFY `id` int(50) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=966;

--
-- AUTO_INCREMENT for table `commandes`
--
ALTER TABLE `commandes`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `commentaires`
--
ALTER TABLE `commentaires`
  MODIFY `id` int(20) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `demandesajout`
--
ALTER TABLE `demandesajout`
  MODIFY `id_dm` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `factures`
--
ALTER TABLE `factures`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `gigs`
--
ALTER TABLE `gigs`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `messages`
--
ALTER TABLE `messages`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `articles`
--
ALTER TABLE `articles`
  ADD CONSTRAINT `FK_articles` FOREIGN KEY (`auteur`) REFERENCES `utilisateurs` (`email`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `utilisateurs`
--
ALTER TABLE `utilisateurs`
  ADD CONSTRAINT `FK_utilisateurs` FOREIGN KEY (`adresseId`) REFERENCES `adresses` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
