package principal

import controlador.JuegoBloque

import javax.swing.UIManager


object Juego extends App :
   try UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName)
   catch {
      case e: Exception =>
         e.printStackTrace()
   }
   new JuegoBloque
end Juego
