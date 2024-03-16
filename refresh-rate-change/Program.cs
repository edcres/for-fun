using Microsoft.Win32;
using System;

namespace DisplaySettingsListener {
    class Program {
        static void Main(string[] args) {
            Console.WriteLine("Listening for display settings changes...");
            SystemEvents.DisplaySettingsChanged += OnDisplaySettingsChanged;
            
            // Prevents the application from exiting immediately
            Console.WriteLine("Press 'Enter' to exit.");
            Console.ReadLine();
        }

        private static void OnDisplaySettingsChanged(object sender, EventArgs e) {
            // This is where you could add code to check and adjust the refresh rate if needed
            Console.WriteLine("Display settings changed.");
        }
    }
}
